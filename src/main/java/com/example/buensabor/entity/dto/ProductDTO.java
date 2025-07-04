package com.example.buensabor.entity.dto;

import java.util.List;

import com.example.buensabor.Bases.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends BaseDTO{
    private BaseDTO company;
    private CategoryDTO category;
    private String title;
    private String description;
    private int estimatedTime;
    private double price;
    private int profit_percentage;
    private String promotionType;
    private Double promotionalPrice; // null si no aplica promo
    private String promotionDescription; // opcional si querés mostrar info de la promo
    private double promotionalExtraValue; // opcional si querés mostrar info de la promo
    private String image;
    private List<ProductIngredientDTO> productIngredients;
}