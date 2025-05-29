package com.example.buensabor.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.CategoryDTO;
import com.example.buensabor.entity.mappers.CategoryMapper;
import com.example.buensabor.repository.CategoryRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.service.interfaces.ICategoryService;

@Service
public class CategoryService extends BaseServiceImplementation<CategoryDTO, Category, Long> implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CompanyRepository companyRepository, CategoryMapper categoryMapper) {
        super(categoryRepository, categoryMapper);
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional
    public CategoryDTO save(CategoryDTO dto) throws Exception {

        Company company = companyRepository.findById(dto.getCompany().getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        Category parentEntity = null;
        if (dto.getParent() != null) {
            parentEntity = categoryRepository.findById(dto.getParent().getId())
                    .orElseThrow(() -> new Exception("Parent not found"));
        }

        Category entity = categoryMapper.toEntity(dto);

        entity.setParent(parentEntity);
        entity.setCompany(company);

        System.out.println(entity.getName() + "  " + entity.getParent());

        Category savedEntity = categoryRepository.save(entity);

        System.out.println(savedEntity.getParent());

        return categoryMapper.toDTO(savedEntity);
    }
}
