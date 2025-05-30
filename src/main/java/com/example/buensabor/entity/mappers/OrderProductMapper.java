package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.OrderProduct;
import com.example.buensabor.entity.dto.OrderProductDTO;

@Mapper(componentModel = "spring")
public interface OrderProductMapper extends BaseMapper<OrderProduct, OrderProductDTO> {
    @Override
    OrderProductDTO toDTO(OrderProduct entity);

    @Override
    OrderProduct toEntity(OrderProductDTO dto);
}