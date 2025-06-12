package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Payment;
import com.example.buensabor.entity.dto.PaymentDTO;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends BaseMapper<Payment, PaymentDTO> {

    @Override
    Payment toEntity(PaymentDTO entity);
    
    @Override
    PaymentDTO toDTO(Payment dto);
}
