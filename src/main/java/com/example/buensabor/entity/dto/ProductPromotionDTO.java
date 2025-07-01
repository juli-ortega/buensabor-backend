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
public class ProductPromotionDTO extends BaseDTO {
    private ProductBasicDTO product;
    private Long productId;
    private Long promotionId;
    private Double value;
    private Double extraValue;
}
