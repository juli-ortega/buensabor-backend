package com.example.buensabor.entity.dto;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.dto.ProductDtos.ProductBasicDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDTO extends BaseDTO {
    private OrderDTO order;
    private ProductBasicDTO product;
    private int quantity;
    private double price;
    private String clarifications;
}
