package com.example.buensabor.entity.dto;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.enums.UnitMeasure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO extends BaseDTO{

    private BaseDTO company;
    private String name;
    private double price;
    private double quantity;
    private UnitMeasure unitMeasure;
    private boolean status;
    private double minStock;
    private double currentStock;
    private double maxStock;
    private CategoryIngredientDTO categoryIngredient;
}
