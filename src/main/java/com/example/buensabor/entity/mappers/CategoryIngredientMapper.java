package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryIngredientMapper extends BaseMapper<CategoryIngredient, CategoryIngredientDTO> {

    @Override
    @Mapping(source = "parent.id", target = "parentId")
    CategoryIngredientDTO toDTO(CategoryIngredient entity);

    @Override
    @Mapping(source = "parentId", target = "parent.id")
    CategoryIngredient toEntity(CategoryIngredientDTO dto);
}

