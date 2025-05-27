package com.example.buensabor.entity.dto;

import com.example.buensabor.Bases.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryIngredientDTO extends BaseDTO{
    private String name;
    private CategoryIngredientDTO parent;
}
