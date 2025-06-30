package com.example.buensabor.service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.Order;
import com.example.buensabor.entity.OrderProduct;
import com.example.buensabor.entity.Payment;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.ProductIngredient;
import com.example.buensabor.entity.ProductPromotion;
import com.example.buensabor.entity.Promotion;
import com.example.buensabor.entity.dto.OrderDTO;
import com.example.buensabor.entity.dto.CreateDTOs.OrderCreateDTO;
import com.example.buensabor.entity.dto.CreateDTOs.OrderProductCreateDTO;
import com.example.buensabor.entity.dto.OrderDTOs.OrderResponseDTO;
import com.example.buensabor.entity.dto.UpdateDTOs.OrderUpdateDTO;
import com.example.buensabor.entity.enums.DeliveryType;
import com.example.buensabor.entity.enums.OrderStatus;
import com.example.buensabor.entity.enums.PayForm;
import com.example.buensabor.entity.enums.PayStatus;
import com.example.buensabor.entity.mappers.OrderMapper;
import com.example.buensabor.repository.ClientRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.IngredientRepository;
import com.example.buensabor.repository.OrderProductRepository;
import com.example.buensabor.repository.OrderRepository;
import com.example.buensabor.repository.PaymentRepository;
import com.example.buensabor.repository.ProductIngredientRepository;
import com.example.buensabor.repository.ProductPromotionRepository;
import com.example.buensabor.repository.ProductRepository;
import com.example.buensabor.service.interfaces.IOrderService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import jakarta.transaction.Transactional;

@Service
public class OrderService extends BaseServiceImplementation<OrderDTO, Order, Long> implements IOrderService {
    
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymenRepository;
    private final ProductIngredientRepository productIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final PaymentService paymentService;
    private final PromotionService promotionService;
    private final ProductPromotionRepository productPromotionRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CompanyRepository companyRepository, ClientRepository clientRepository, ProductRepository productRepository, PaymentService paymentService, PaymentRepository paymentRepository, ProductIngredientRepository productIngredientRepository, IngredientRepository ingredientRepository, ProductPromotionRepository productPromotionRepository, PromotionService promotionService) {
        super(orderRepository, orderMapper);
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.companyRepository = companyRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.paymentService = paymentService;
        this.paymenRepository = paymentRepository;
        this.productIngredientRepository = productIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.productPromotionRepository = productPromotionRepository;
        this.promotionService = promotionService;
    }

    public List<OrderResponseDTO> getCompanyOrders() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Compania no encontrada"));

        List<Order> orders = orderRepository.findByCompanyIdOrderByInitAtDesc(company.getId());

        return orderMapper.toSummaryDTOList(orders);
    }


    public List<OrderResponseDTO> getClientOrders() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Client client = clientRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Order> orders = orderRepository.findByClientIdOrderByInitAtDesc(client.getId());

        return orderMapper.toSummaryDTOList(orders);
    }   

    @Transactional
    public String save(OrderCreateDTO orderCreateDTO) throws MPApiException, MPException {
        // Obtener usuario autenticado
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Buscar cliente asociado al usuario autenticado
        Client client = clientRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<OrderProductCreateDTO> orderProductCreateDTOs = orderCreateDTO.getOrderProductDTOs();

        if (orderProductCreateDTOs.isEmpty()) {
            throw new RuntimeException("No hay productos en la orden");
        }

        // Tomar primer producto para obtener la company
        Product firstProduct = productRepository.findById(orderProductCreateDTOs.get(0).getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado " + orderProductCreateDTOs.get(0).getProductId()));

        Company company = companyRepository.findById(firstProduct.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Compania no encontrada"));

        // Verificar stock de todos los productos
        for (OrderProductCreateDTO opCreateDTO : orderProductCreateDTOs) {
            Product product = productRepository.findById(opCreateDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado " + opCreateDTO.getProductId()));

            List<ProductIngredient> productIngredients = productIngredientRepository.findByProductId(product.getId());

            for (ProductIngredient pi : productIngredients) {
                double cantidadNecesaria = pi.getQuantity() * opCreateDTO.getQuantity();
                Ingredient ingrediente = pi.getIngredient();

                if (ingrediente.getCurrentStock() < cantidadNecesaria) {
                    throw new RuntimeException("No hay suficiente stock de: " + ingrediente.getName());
                }
            }
        }

        // Crear orden base
        Order order = new Order();
        order.setDescription(orderCreateDTO.getDescription());
        order.setDeliveryType(orderCreateDTO.getDeliveryType());
        order.setClient(client);
        order.setInitAt(new Date());
        order.setStatus(orderCreateDTO.getPayForm() == PayForm.MERCADO_PAGO ? OrderStatus.PENDING_PAYMENT : OrderStatus.TOCONFIRM);
        order.setCompany(company);

        List<OrderProduct> orderProducts = new ArrayList<>();
        double total = 0;

        for (OrderProductCreateDTO opCreateDTO : orderProductCreateDTOs) {
            Product product = productRepository.findById(opCreateDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + opCreateDTO.getProductId()));

            // Descontar stock
            List<ProductIngredient> productIngredients = productIngredientRepository.findByProductId(product.getId());
            for (ProductIngredient pi : productIngredients) {
                double cantidadNecesaria = pi.getQuantity() * opCreateDTO.getQuantity();
                Ingredient ingrediente = pi.getIngredient();
                ingrediente.setCurrentStock(ingrediente.getCurrentStock() - cantidadNecesaria);
                ingredientRepository.save(ingrediente);
            }

            // Obtener promoción aplicable
            Optional<Promotion> applicablePromotion = promotionService.getApplicablePromotion(product, company);

            double priceToApply;

            if (applicablePromotion.isPresent()) {
                Promotion promotion = applicablePromotion.get();

                Optional<ProductPromotion> productPromotionOpt = productPromotionRepository.findByProductAndPromotion(
                        product.getId(), promotion.getId()
                );

                priceToApply = productPromotionOpt
                        .map(ProductPromotion::getValue)
                        .orElse(product.getPrice());

            } else {
                priceToApply = product.getPrice();
            }

            if (orderCreateDTO.getDeliveryType() == DeliveryType.TAKEAWAY) {
                priceToApply = priceToApply * 0.90;  // Descuento 10%
            } else if (orderCreateDTO.getDeliveryType() == DeliveryType.DELIVERY) {
                priceToApply = priceToApply * 1.10;  // Recargo 10%
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(opCreateDTO.getQuantity());
            orderProduct.setClarifications(opCreateDTO.getClarifications());
            orderProduct.setPrice(priceToApply * opCreateDTO.getQuantity());

            total += orderProduct.getPrice();

            orderProducts.add(orderProduct);
        }


        // Actualizar total y orderProducts
        order.setTotal(total);
        order.setOrderProducts(orderProducts);
        orderRepository.save(order);

        // Crear pago
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPayStatus(PayStatus.pending);
        payment.setAmount(total);

        if (orderCreateDTO.getPayForm() == PayForm.MERCADO_PAGO) {
            Preference preference = paymentService.createPreference(order);
            payment.setMercadoPagoId(preference.getClientId());
            return preference.getInitPoint();
        }

        paymenRepository.save(payment);

        return "The order created successfully";
    }


    @Transactional
    public OrderDTO updateDescription(Long orderId, OrderUpdateDTO orderUpdateDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        order.setDescription(orderUpdateDTO.getDescription());
        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public OrderDTO cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        order.setStatus(OrderStatus.CANCELLED);
        order.setFinalizedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        order.setStatus(newStatus);

        // Si pasa a DELIVERED, seteamos finalizedAt
        if (newStatus == OrderStatus.DELIVERED) {
            order.setFinalizedAt(new Date());
        }

        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toDTO(updatedOrder);
    }



}
