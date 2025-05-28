package com.example.buensabor.entity.dto;

import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.enums.UnitMeasure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {

    private Long id;
    private Company company;
    private String name;
    private double price;
    private double quantity;
    private UnitMeasure unitMeasure;
    private boolean status;
    private double minStock;
    private double currentStock;
    private double maxStock;
    private CategoryIngredient category;
}
