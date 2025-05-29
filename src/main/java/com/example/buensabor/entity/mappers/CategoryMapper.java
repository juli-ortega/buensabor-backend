package com.example.buensabor.entity.mappers;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<Category, CategoryDTO> {

    @Override
    @Mapping(source = "parent", target = "parent", qualifiedByName = "mapParent")
    CategoryDTO toDTO(Category entity);

    @Override
    @Mapping(source = "parent", target = "parent")
    Category toEntity(CategoryDTO dto);

    @Named("mapParent")
    default CategoryDTO mapParent(Category parent) {
        if (parent == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(parent.getId());
        dto.setName(parent.getName());

        if (parent.getCompany() != null) {
            BaseDTO companyDTO = new BaseDTO();
            companyDTO.setId(parent.getCompany().getId());
            dto.setCompany(companyDTO);
        }

        dto.setParent(null); // evitar recursión al abuelo

        return dto;
    }
}
