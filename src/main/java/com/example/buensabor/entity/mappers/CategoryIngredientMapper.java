package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;

@Mapper(componentModel = "spring")
public interface CategoryIngredientMapper extends BaseMapper<CategoryIngredient, CategoryIngredientDTO> {

    @Mapping(target = "parent", ignore = true)
    @Override
    CategoryIngredientDTO toDTO(CategoryIngredient entity);

    @Mapping(target = "parent", ignore = true)
    @Override
    CategoryIngredient toEntity(CategoryIngredientDTO dto);
}

