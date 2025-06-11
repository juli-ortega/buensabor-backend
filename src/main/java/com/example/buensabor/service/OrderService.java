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
import com.example.buensabor.entity.dto.ProductDTO;
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

    @Override
    @Transactional
    public OrderDTO save(OrderDTO orderDTO) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // Verificar que la Company existe
        Client client = clientRepository.findById(userDetails.getId())
        .orElseThrow(() -> new RuntimeException("Client not found"));

        List<OrderProductDTO> orderProductDTO = orderDTO.getOrderProductDTOs();

        if (orderProductDTO.isEmpty()) {
            throw new RuntimeException("There aren't products in the order");
        }

        // Convertir DTO a entidad
        Order order = orderMapper.toEntity(orderDTO);
        order.setClient(client);
        order.setInitAt(new java.util.Date());
        order.setStatus(OrderStatus.TOCONFIRM);
        
        Company company = companyRepository.findById(orderProductDTO.get(0).getProduct().getCompany().getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));
        
        order.setCompany(company);
        

        double total = 0;
        // Validar y asociar los productos al OrderProduct
        for (OrderProductDTO orderProductDTO2 : orderProductDTO) {
            
            Product product = productRepository.findById(orderProductDTO2.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + orderProductDTO2.getProduct().getId()));
            
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(orderProductDTO2.getQuantity());
            orderProduct.setPrice(product.getPrice() * orderProductDTO2.getQuantity());
            total = total + orderProduct.getPrice();
            
            orderProductRepository.save(orderProduct);
        }
        
        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        // Retornar DTO
        return orderMapper.toDTO(savedOrder);
    }

}
