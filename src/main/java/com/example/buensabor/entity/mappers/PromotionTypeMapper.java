package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.PromotionType;
import com.example.buensabor.entity.dto.PromotionTypeDTO;

@Mapper(componentModel = "spring")
public interface PromotionTypeMapper extends BaseMapper<PromotionType, PromotionTypeDTO> {
    
    @Override
    @Mapping(source = "companyId", target = "company.id")
    PromotionTypeDTO toDTO(PromotionType entity);

    @Override
    @Mapping(source = "company.id", target = "companyId")
    PromotionType toEntity(PromotionTypeDTO dto);
}