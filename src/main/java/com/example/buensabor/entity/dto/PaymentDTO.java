package com.example.buensabor.entity.dto;

import java.util.Date;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.enums.PayStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO extends BaseDTO {
    private OrderDTO orderDTO;
    private String mercadoPagoId;
    private PayStatus payStatus;
    private double amount;
    private Date approvedDate;
}
