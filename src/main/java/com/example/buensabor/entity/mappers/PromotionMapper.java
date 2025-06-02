package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Promotion;
import com.example.buensabor.entity.dto.PromotionDTO;

@Mapper(componentModel = "spring")
public interface PromotionMapper extends BaseMapper<Promotion, PromotionDTO> {
    @Override
    PromotionDTO toDTO(Promotion entity);

    @Override
    Promotion toEntity(PromotionDTO dto);
}