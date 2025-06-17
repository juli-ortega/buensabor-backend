package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.PromotionType;
import com.example.buensabor.entity.dto.PromotionTypeDTO;

@Mapper(componentModel = "spring")
public interface PromotionTypeMapper extends BaseMapper<PromotionType, PromotionTypeDTO> {
    
    @Override
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "name", target = "name")
    PromotionTypeDTO toDTO(PromotionType entity);

    @Override
    @Mapping(source = "companyId", target = "company.id")
    @Mapping(source = "name", target = "name")
    PromotionType toEntity(PromotionTypeDTO dto);
}