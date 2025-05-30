package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.BuyIngredient;
import com.example.buensabor.entity.dto.BuyIngredientDTO;

@Mapper(componentModel = "spring")
public interface BuyIngredientMapper extends BaseMapper<BuyIngredient, BuyIngredientDTO> {
    @Override
    BuyIngredientDTO toDTO(BuyIngredient entity);

    @Override
    BuyIngredient toEntity(BuyIngredientDTO dto);
}