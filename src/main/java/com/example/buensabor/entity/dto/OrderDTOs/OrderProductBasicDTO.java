package com.example.buensabor.entity.dto.OrderDTOs;

import com.example.buensabor.Bases.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductBasicDTO extends BaseDTO {
    private Long orderId;
    private Long productId;
    private String title; // <-- agregado para el título del producto
    private int quantity;
    private double price;
}
