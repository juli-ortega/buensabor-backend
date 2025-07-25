package com.example.buensabor.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.Util.SecurityUtil;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.CategoryDTO;
import com.example.buensabor.entity.mappers.CategoryMapper;
import com.example.buensabor.repository.CategoryRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.service.interfaces.ICategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService extends BaseServiceImplementation<CategoryDTO, Category, Long> implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CompanyRepository companyRepository;
    private final CategoryMapper categoryMapper;
    private final SecurityUtil securityUtil;

    public CategoryService(CategoryRepository categoryRepository, SecurityUtil securityUtil, CompanyRepository companyRepository, CategoryMapper categoryMapper) {
        super(categoryRepository, categoryMapper);
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.categoryMapper = categoryMapper;
        this.securityUtil = securityUtil;
    }

    @Override
    @Transactional
    public CategoryDTO save(CategoryDTO dto) throws Exception {
        
        Company company = securityUtil.getAuthenticatedCompany();

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

    @Override
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) throws Exception {

        // Verificar que la Company exist
        Company company = securityUtil.getAuthenticatedCompany();

        // Buscar la categoría existente
        Category entity = categoryRepository.findById(id)
            .orElseThrow(() -> new Exception("Category not found"));

        // Verificar que la categoría pertenece a la misma empresa
        if (!entity.getCompany().getId().equals(company.getId())) {
            throw new Exception("You don't have permission to update this category");
        }

        // Si tiene un parent nuevo, buscarlo y asignarlo
        Category parentEntity = null;
        if (dto.getParent() != null) {
            parentEntity = categoryRepository.findById(dto.getParent().getId())
                    .orElseThrow(() -> new Exception("Parent not found"));
        }

        // Actualizar campos
        entity.setName(dto.getName());
        entity.setIsActive(dto.getIsActive());
        entity.setParent(parentEntity);

        // Guardar cambios
        Category updatedEntity = categoryRepository.save(entity);

        return categoryMapper.toDTO(updatedEntity);
    }


    public List<CategoryDTO> findAll() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            List<CategoryDTO> all = super.findAll();
            all.removeIf(c -> c.getIsActive() != null && !c.getIsActive());
            return all;
        } else {
            Company company = securityUtil.getAuthenticatedCompany();
            List<Category> list = categoryRepository.findAll().stream()
                .filter(c -> c.getCompany() != null && c.getCompany().getId().equals(company.getId()))
                .filter(c -> c.getIsActive() == null || c.getIsActive())
                .collect(Collectors.toList());
            return list.stream().map(categoryMapper::toDTO).collect(Collectors.toList());
        }
    }

    public List<CategoryDTO> findAllByCompanyId(Long companyId) throws Exception {
        if (companyId == null) {
            throw new IllegalArgumentException("companyId no puede ser nulo");
        }

        List<Category> list = categoryRepository.findAll().stream()
            .filter(c -> c.getCompany() != null && c.getCompany().getId().equals(companyId))
            .filter(c -> c.getIsActive() == null || c.getIsActive())
            .collect(Collectors.toList());

        return list.stream()
            .map(categoryMapper::toDTO)
            .collect(Collectors.toList());
    }


    public CategoryDTO findById(Long id) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        Category entity = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("CCategoria no encontrada"));
        if (isAdmin || (entity.getCompany() != null && entity.getCompany().getId().equals(userDetails.getId()))) {
            return categoryMapper.toDTO(entity);
        } else {
            throw new RuntimeException("No tiene permiso para acceder a este recurso");
        }
    }
}
