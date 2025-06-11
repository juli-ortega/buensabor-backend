package com.example.buensabor.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;
import com.example.buensabor.entity.mappers.CategoryIngredientMapper;
import com.example.buensabor.repository.CategoryIngredientRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.service.interfaces.ICategoryIngredientService;

@Service
public class CategoryIngredientService extends BaseServiceImplementation<CategoryIngredientDTO, CategoryIngredient, Long> implements ICategoryIngredientService{

    private final CategoryIngredientRepository categoryIngredientRepository;
    
    private final CategoryIngredientMapper categoryIngredientMapper;
    private final CompanyRepository companyRepository;

    public CategoryIngredientService(CategoryIngredientRepository categoryIngredientRepository, CategoryIngredientMapper categoryIngredientMapper, CompanyRepository companyRepository) {
        super(categoryIngredientRepository, categoryIngredientMapper);
        this.categoryIngredientRepository = categoryIngredientRepository;
        this.categoryIngredientMapper = categoryIngredientMapper;
        this.companyRepository = companyRepository;
    }

    @Override
    public CategoryIngredientDTO save(CategoryIngredientDTO dto) throws Exception {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Company company = companyRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        if (dto.getParent() != null && !categoryIngredientRepository.existsById(dto.getParent().getId())) {
            throw new Exception("Parent not found");
        }

        CategoryIngredient entity = categoryIngredientMapper.toEntity(dto);
        entity.setCompany(company);
        
        CategoryIngredient savedEntity = categoryIngredientRepository.save(entity);
        return categoryIngredientMapper.toDTO(savedEntity);
    }

}