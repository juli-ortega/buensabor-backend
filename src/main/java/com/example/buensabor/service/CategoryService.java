package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.dto.CategoryDTO;
import com.example.buensabor.entity.mappers.CategoryMapper;
import com.example.buensabor.repository.CategoryRepository;
import com.example.buensabor.service.interfaces.ICategoryService;

@Service
public class CategoryService extends BaseServiceImplementation<CategoryDTO, Category, Long> implements ICategoryService {

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        super(categoryRepository, categoryMapper);
    }
}
