package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;

@Mapper(componentModel = "spring")
public interface CategoryIngredientMapper extends BaseMapper<CategoryIngredient, CategoryIngredientDTO> {

    @Mapping(target = "parent", source = "parent", qualifiedByName = "mapParentIdToCategoryIngredient")
    CategoryIngredient toEntity(CategoryIngredientDTO dto);

    @Mapping(target = "parent", source = "parent.id")
    CategoryIngredientDTO toDTO(CategoryIngredient entity);

    @Named("mapParentIdToCategoryIngredient")
    default CategoryIngredient mapParentIdToCategoryIngredient(Long parentId) {
        if (parentId == null) return null;
        CategoryIngredient parent = new CategoryIngredient();
        parent.setId(parentId);
        return parent;
    }
}

