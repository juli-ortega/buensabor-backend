package com.example.buensabor.entity.mappers;
import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.dto.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper<Product, ProductDTO> {

    @Override
    ProductDTO toDTO(Product entity);

    @Override
    Product toEntity(ProductDTO dto);
}
