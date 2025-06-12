package com.example.buensabor.service;


import java.util.Date;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Order;
import com.example.buensabor.entity.OrderProduct;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.dto.OrderDTO;
import com.example.buensabor.entity.dto.OrderProductDTO;
import com.example.buensabor.entity.dto.CreateDTOs.OrderCreateDTO;
import com.example.buensabor.entity.dto.CreateDTOs.OrderProductCreateDTO;
import com.example.buensabor.entity.dto.UpdateDTOs.OrderUpdateDTO;
import com.example.buensabor.entity.enums.OrderStatus;
import com.example.buensabor.entity.mappers.OrderMapper;
import com.example.buensabor.repository.ClientRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.OrderProductRepository;
import com.example.buensabor.repository.OrderRepository;
import com.example.buensabor.repository.ProductRepository;
import com.example.buensabor.service.interfaces.IOrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderService extends BaseServiceImplementation<OrderDTO, Order, Long> implements IOrderService {
    
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CompanyRepository companyRepository, ClientRepository clientRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        super(orderRepository, orderMapper);
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.companyRepository = companyRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public OrderDTO save(OrderCreateDTO orderCreateDTO) {
        // Obtener usuario autenticado
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Buscar cliente asociado al usuario autenticado
        Client client = clientRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        List<OrderProductCreateDTO> orderProductCreateDTOs = orderCreateDTO.getOrderProductDTOs();

        if (orderProductCreateDTOs.isEmpty()) {
            throw new RuntimeException("There aren't products in the order");
        }

        // Tomar primer producto para obtener la company
        Product firstProduct = productRepository.findById(orderProductCreateDTOs.get(0).getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + orderProductCreateDTOs.get(0).getProductId()));

        Company company = companyRepository.findById(firstProduct.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // Crear orden base
        Order order = new Order();
        order.setDescription(orderCreateDTO.getDescription());
        order.setDeliveryType(orderCreateDTO.getDeliveryType());
        order.setClient(client);
        order.setInitAt(new Date());
        order.setStatus(OrderStatus.TOCONFIRM);
        order.setCompany(company);

        // Guardar orden para generar ID
        Order savedOrder = orderRepository.save(order);

        double total = 0;

        // Asociar productos a la orden
        for (OrderProductCreateDTO opCreateDTO : orderProductCreateDTOs) {
            Product product = productRepository.findById(opCreateDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + opCreateDTO.getProductId()));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(savedOrder); // orden ya con ID
            orderProduct.setProduct(product);
            orderProduct.setQuantity(opCreateDTO.getQuantity());
            orderProduct.setPrice(product.getPrice() * opCreateDTO.getQuantity());

            total += orderProduct.getPrice();

            orderProductRepository.save(orderProduct);
        }

        // Actualizar total de la orden
        savedOrder.setTotal(total);
        orderRepository.save(savedOrder); // actualizar orden con total

        // Retornar DTO de la orden
        return orderMapper.toDTO(savedOrder);
    }

    @Transactional
    public OrderDTO updateDescription(Long orderId, OrderUpdateDTO orderUpdateDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDescription(orderUpdateDTO.getDescription());
        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public OrderDTO cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        order.setFinalizedAt(new Date());

        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toDTO(updatedOrder);
    }



}
