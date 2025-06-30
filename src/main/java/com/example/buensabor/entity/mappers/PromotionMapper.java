package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.ProductPromotion;
import com.example.buensabor.entity.Promotion;
import com.example.buensabor.entity.dto.ProductPromotionDTO;
import com.example.buensabor.entity.dto.PromotionDTO;
import com.example.buensabor.entity.dto.CreateDTOs.PromotionCreateDTO;

@Mapper(componentModel = "spring")
public interface PromotionMapper extends BaseMapper<Promotion, PromotionDTO> {
    
    @Override
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "promotionType.id", target = "promotionTypeDTO.id")
    @Mapping(source = "dayOfWeeks", target = "dayOfWeeks") // Mapeo explícito para los días
    @Mapping(target = "productPromotions", source = "productPromotions")
    PromotionDTO toDTO(Promotion entity);

    @Override
    @Mapping(source = "companyId", target = "company.id")
    @Mapping(source = "promotionTypeDTO.id", target = "promotionType.id")
    Promotion toEntity(PromotionDTO dto);

    @Mapping(source = "promotionTypeId", target = "promotionType.id")
    @Mapping(source = "dayOfWeeks", target = "dayOfWeeks") // Mapeo de días al crear
    Promotion toEntity(PromotionCreateDTO dto);

    @Mapping(source = "value", target = "value")
    @Mapping(source = "extraValue", target = "extraValue")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "promotion.id", target = "promotionId")
    ProductPromotionDTO toDTO(ProductPromotion productPromotion);

}