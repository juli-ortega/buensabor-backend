package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;

@Mapper(componentModel = "spring")
public interface CategoryIngredientMapper extends BaseMapper<CategoryIngredient, CategoryIngredientDTO> {
    @Override
    @Mapping(source = "parent", target = "parent", qualifiedByName = "mapParent")
    CategoryIngredientDTO toDTO(CategoryIngredient entity);

    @Override
    @Mapping(source = "parent", target = "parent")
    CategoryIngredient toEntity(CategoryIngredientDTO dto);

    @Named("mapParent")
    default CategoryIngredientDTO mapParent(CategoryIngredient parent) {
        if (parent == null) {
            return null;
        }

        CategoryIngredientDTO dto = new CategoryIngredientDTO();
        dto.setId(parent.getId());
        dto.setName(parent.getName());

        // Mapear solo el id de la company si existe
        if (parent.getCompany() != null) {
            BaseDTO companyDTO = new BaseDTO();
            companyDTO.setId(parent.getCompany().getId());
            dto.setCompany(companyDTO);
        }

        dto.setParent(null); // no mapeamos recursivamente al abuelo

        return dto;
    }

}

