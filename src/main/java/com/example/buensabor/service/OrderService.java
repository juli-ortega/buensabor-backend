package com.example.buensabor.service;


import java.util.Date;

import org.springframework.stereotype.Service;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Order;
import com.example.buensabor.entity.dto.OrderDTO;
import com.example.buensabor.entity.enums.OrderStatus;
import com.example.buensabor.entity.mappers.OrderMapper;
import com.example.buensabor.repository.OrderRepository;
import com.example.buensabor.service.interfaces.IOrderService;

@Service
public class OrderService extends BaseServiceImplementation<OrderDTO, Order, Long> implements IOrderService {
    
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        super(orderRepository, orderMapper);
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        // Convertir DTO a entidad
        Order order = orderMapper.toEntity(orderDTO);

        // Setear initAt si viene null
        if (order.getInitAt() == null) {
            order.setInitAt(new Date());
        }

        // Setear status inicial si viene null
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.TOCONFIRM);
        }

        // Guardar la entidad
        Order savedOrder = orderRepository.save(order);

        // Convertir de nuevo a DTO para devolver
        return orderMapper.toDTO(savedOrder);
    }
}
