package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Order;
import com.example.buensabor.entity.dto.OrderDTO;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<Order, OrderDTO> {
    @Override
    OrderDTO toDTO(Order entity);

    @Override
    Order toEntity(OrderDTO dto);
}