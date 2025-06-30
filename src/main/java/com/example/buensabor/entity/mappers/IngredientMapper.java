package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.entity.dto.CreateDTOs.IngredientCreateDTO;

@Mapper(componentModel = "spring")
public interface IngredientMapper extends BaseMapper<Ingredient, IngredientDTO> {

    @Override
    IngredientDTO toDTO(Ingredient entity);

    @Override
    Ingredient toEntity(IngredientDTO dto);

    Ingredient toEntity(IngredientCreateDTO dto);
}