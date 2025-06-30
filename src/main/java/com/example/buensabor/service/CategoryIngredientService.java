package com.example.buensabor.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
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

import java.util.List;
import java.util.stream.Collectors;

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
            .orElseThrow(() -> new RuntimeException("Compania no encontrada "));

        if (dto.getParent() != null && !categoryIngredientRepository.existsById(dto.getParent().getId())) {
            throw new Exception("Parent not found");
        }

        CategoryIngredient entity = categoryIngredientMapper.toEntity(dto);
        entity.setCompany(company);
        
        CategoryIngredient savedEntity = categoryIngredientRepository.save(entity);
        return categoryIngredientMapper.toDTO(savedEntity);
    }

    @Override
    public CategoryIngredientDTO update(Long id, CategoryIngredientDTO dto) throws Exception {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Company company = companyRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Compañía no encontrada"));

        CategoryIngredient entity = categoryIngredientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría de ingrediente no encontrada"));

        // Verificar que la categoría pertenezca a la misma compañía
        if (entity.getCompany() == null || !entity.getCompany().getId().equals(company.getId())) {
            throw new RuntimeException("No tiene permisos para actualizar esta categoría.");
        }

        // Actualiza los campos básicos
        entity.setName(dto.getName());

        // Actualiza el padre solo si es diferente
        if (dto.getParent() != null) {
            CategoryIngredient newParent = categoryIngredientRepository.findById(dto.getParent().getId())
                .orElseThrow(() -> new RuntimeException("Categoría padre no encontrada"));

            // Verificar que el nuevo padre sea de la misma compañía
            if (newParent.getCompany() == null || !newParent.getCompany().getId().equals(company.getId())) {
                throw new RuntimeException("No tiene permisos para asignar esta categoría padre.");
            }

            entity.setParent(newParent);
        } else {
            entity.setParent(null);
        }

        // Asegurar que la compañía esté bien seteada (por si quedó inconsistente)
        entity.setCompany(company);

        CategoryIngredient saved = categoryIngredientRepository.save(entity);
        return categoryIngredientMapper.toDTO(saved);
    }


    @Override
    public boolean delete(Long id) throws Exception {
        CategoryIngredient entity = categoryIngredientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria ingrediente no encontrada"));

        // Borrar recursivamente todos los hijos
        deleteChildrenRecursive(entity);

        categoryIngredientRepository.delete(entity);
        return true;
    }

    private void deleteChildrenRecursive(CategoryIngredient parent) {
        List<CategoryIngredient> children = categoryIngredientRepository.findAll().stream()
            .filter(ci -> ci.getParent() != null && ci.getParent().getId().equals(parent.getId()))
            .collect(Collectors.toList());
        for (CategoryIngredient child : children) {
            deleteChildrenRecursive(child);
            categoryIngredientRepository.delete(child);
        }
    }

    public List<CategoryIngredientDTO> findAll() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            List<CategoryIngredientDTO> all = super.findAll();
            all.removeIf(c -> c.getIsActive() != null && !c.getIsActive());
            return all;
        } else {
            Company company = companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
            List<CategoryIngredient> list = categoryIngredientRepository.findAll().stream()
                .filter(ci -> ci.getCompany() != null && ci.getCompany().getId().equals(company.getId()))
                .filter(ci -> ci.getIsActive() == null || ci.getIsActive())
                .collect(Collectors.toList());
            return list.stream().map(categoryIngredientMapper::toDTO).collect(Collectors.toList());
        }
    }

    public CategoryIngredientDTO findById(Long id) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        CategoryIngredient entity = categoryIngredientRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria ingrediente no encontrada"));
        if (isAdmin || (entity.getCompany() != null && entity.getCompany().getId().equals(userDetails.getId()))) {
            return categoryIngredientMapper.toDTO(entity);
        } else {
            throw new RuntimeException("No tiene permiso para acceder a este recurso");
        }
    }

}