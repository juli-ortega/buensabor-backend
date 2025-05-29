package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;

@Mapper(componentModel = "spring")
public interface CategoryIngredientMapper extends BaseMapper<CategoryIngredient, CategoryIngredientDTO> {

    CategoryIngredient toEntity(CategoryIngredientDTO dto);

    CategoryIngredientDTO toDTO(CategoryIngredient entity);

}

