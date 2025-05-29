package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.dto.CategoryDTO;
import com.example.buensabor.entity.dto.ProductDTO;

@Mapper(componentModel = "spring", uses = {ProductIngredientMapper.class, CategoryMapper.class})
public interface ProductMapper extends BaseMapper<Product, ProductDTO> {

    @Override
    @Mapping(source = "category", target = "category", qualifiedByName = "mapCategory")
    ProductDTO toDTO(Product entity);

    @Override
    @Mapping(source = "category", target = "category")
    Product toEntity(ProductDTO dto);

    @Named("mapCategory")
    default CategoryDTO mapCategory(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());

        if (category.getCompany() != null) {
            BaseDTO companyDTO = new BaseDTO();
            companyDTO.setId(category.getCompany().getId());
            dto.setCompany(companyDTO);
        }

        dto.setParent(null);

        return dto;
    }
}
