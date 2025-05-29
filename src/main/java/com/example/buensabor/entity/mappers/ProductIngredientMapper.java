package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.ProductIngredient;
import com.example.buensabor.entity.dto.ProductIngredientDTO;

@Mapper(componentModel = "spring")
public interface ProductIngredientMapper {
    
    @Mapping(target = "ingredient", expression = "java(mapIngredient(entity.getIngredient()))")
    ProductIngredientDTO toDTO(ProductIngredient entity);

    default BaseDTO mapIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        BaseDTO dto = new BaseDTO();
        dto.setId(ingredient.getId());
        return dto;
    }
}


