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
    private String image;
    private List<ProductIngredientDTO> productIngredients;
}