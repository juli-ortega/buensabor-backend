package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.PromotionType;
import com.example.buensabor.entity.dto.PromotionTypeDTO;
import com.example.buensabor.entity.dto.CreateDTOs.PromotionTypeCreateDTO;

@Mapper(componentModel = "spring")
public interface PromotionTypeMapper extends BaseMapper<PromotionType, PromotionTypeDTO> {
    
    @Override
    @Mapping(source = "company.id", target = "companyId")
    PromotionTypeDTO toDTO(PromotionType entity);

    @Override
    @Mapping(source = "companyId", target = "company.id")
    PromotionType toEntity(PromotionTypeDTO dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "behavior", target = "behavior")
    PromotionType toEntity(PromotionTypeCreateDTO dto);
}