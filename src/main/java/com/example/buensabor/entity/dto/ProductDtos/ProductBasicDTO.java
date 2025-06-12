package com.example.buensabor.entity.dto.ProductDtos;

import com.example.buensabor.Bases.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBasicDTO extends BaseDTO {
    private String title;
    private Double price;
    private Long companyId;
}