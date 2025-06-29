package com.example.buensabor.entity.dto.ResponseDTOs;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;
import com.example.buensabor.entity.enums.UnitMeasure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDTO extends BaseDTO {
    private String name;
    private BaseDTO company;
    private double price;
    private boolean isToPrepare;
    private UnitMeasure unitMeasure;
    private boolean status;
    private double minStock;
    private double currentStock;
    private double maxStock;
    private CategoryIngredientDTO categoryIngredient;
}
