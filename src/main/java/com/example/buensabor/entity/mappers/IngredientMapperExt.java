package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.entity.dto.ResponseDTOs.IngredientResponseDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapperExt extends BaseMapper<Ingredient, IngredientDTO> {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "company", target = "company")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "toPrepare", target = "toPrepare")
    @Mapping(source = "unitMeasure", target = "unitMeasure")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "minStock", target = "minStock")
    @Mapping(source = "currentStock", target = "currentStock")
    @Mapping(source = "maxStock", target = "maxStock")
    @Mapping(source = "categoryIngredient", target = "categoryIngredient")
    IngredientResponseDTO toResponseDTO(Ingredient ingredient);

    void updateIngredientFromDTO(IngredientDTO dto, @MappingTarget Ingredient entity);
}
