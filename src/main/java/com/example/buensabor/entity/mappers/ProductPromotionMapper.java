package com.example.buensabor.entity.mappers;
import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.ProductPromotion;
import com.example.buensabor.entity.dto.ProductPromotionDTO;;

@Mapper(componentModel = "spring")
public interface ProductPromotionMapper extends BaseMapper<ProductPromotion, ProductPromotionDTO> {
    
    @Override
    ProductPromotionDTO toDTO(ProductPromotion entity);

    @Override
    ProductPromotion toEntity(ProductPromotionDTO dto);
}
