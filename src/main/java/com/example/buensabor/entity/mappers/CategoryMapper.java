package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.dto.CategoryDTO;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<Category, CategoryDTO> {

    @Override
    CategoryDTO toDTO(Category entity);

    @Override
    Category toEntity(CategoryDTO dto);
}