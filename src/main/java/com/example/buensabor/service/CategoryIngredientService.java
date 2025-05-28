package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;
import com.example.buensabor.entity.mappers.CategoryIngredientMapper;
import com.example.buensabor.repository.CategoryIngredientRepository;
import com.example.buensabor.service.interfaces.ICategoryIngredientService;

@Service
public class CategoryIngredientService extends BaseServiceImplementation<CategoryIngredientDTO, CategoryIngredient, Long> implements ICategoryIngredientService{

    private final CategoryIngredientRepository categoryIngredientRepository;
    
    private final CategoryIngredientMapper categoryIngredientMapper;

    public CategoryIngredientService(CategoryIngredientRepository categoryIngredientRepository, CategoryIngredientMapper categoryIngredientMapper) {
        super(categoryIngredientRepository, categoryIngredientMapper);
        this.categoryIngredientRepository = categoryIngredientRepository;
        this.categoryIngredientMapper = categoryIngredientMapper;
    }

    @Override
    public CategoryIngredientDTO save(CategoryIngredientDTO dto) throws Exception {

        System.out.println(dto.getParent());

        if (dto.getParent() != null && !categoryIngredientRepository.existsById(dto.getParent())) {
            throw new Exception("Parent not found");
        }

        CategoryIngredient entity = categoryIngredientMapper.toEntity(dto);
        
        System.out.println(entity.getName() + "  "+entity.getParent());
        CategoryIngredient savedEntity = categoryIngredientRepository.save(entity);
        return categoryIngredientMapper.toDTO(savedEntity);
    }

}