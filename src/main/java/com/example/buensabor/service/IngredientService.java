package com.example.buensabor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.entity.mappers.IngredientMapper;
import com.example.buensabor.repository.CategoryIngredientRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.IngredientRepository;
import com.example.buensabor.service.interfaces.IIngredientService;

import jakarta.transaction.Transactional;

@Service
public class IngredientService extends BaseServiceImplementation<IngredientDTO,Ingredient, Long> implements IIngredientService {

    private final IngredientRepository ingredientRepository;
    
    private final IngredientMapper ingredientMapper;
    
    private final CompanyRepository companyRepository;
    
    private final CategoryIngredientRepository categoryIngredientRepository;

    public IngredientService(
        IngredientRepository ingredientRepository,
        IngredientMapper ingredientMapper,
        CompanyRepository companyRepository,
        CategoryIngredientRepository categoryIngredientRepository
    ){
        super(ingredientRepository, ingredientMapper);
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.companyRepository = companyRepository;
        this.categoryIngredientRepository = categoryIngredientRepository;
    }

    public List<IngredientDTO> getNotToPrepare(){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ingredient> ingredients = ingredientRepository.findByIsToPrepareFalseAndCompanyId(userDetails.getId());

        return ingredients.stream()
                    .map(ingredientMapper::toDTO)
                    .collect(Collectors.toList());
    }

    public List<IngredientDTO> getToPrepare(){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ingredient> ingredients = ingredientRepository.findByIsToPrepareTrueAndCompanyId(userDetails.getId());
        
        return ingredients.stream()
                    .map(ingredientMapper::toDTO)
                    .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public IngredientDTO save(IngredientDTO ingredientDTO) throws Exception {

        // Verificar que la Company existe
        Company company = companyRepository.findById(ingredientDTO.getCompany().getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        // Verificar que la CategoryIngredient existe
        CategoryIngredient categoryIngredient = categoryIngredientRepository.findById(ingredientDTO.getCategoryIngredient().getId())
            .orElseThrow(() -> new RuntimeException("Category Ingredient not found"));

        // Crear el Ingredient y mapear los datos
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDTO);
        ingredient.setCompany(company);
        ingredient.setCategoryIngredient(categoryIngredient);

        ingredientRepository.save(ingredient);

        return ingredientMapper.toDTO(ingredient);
    }
    
    @Override
    @Transactional
    public IngredientDTO update(Long id, IngredientDTO ingredientDTO) throws Exception {
        // Buscar el ingrediente existente
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        // Verificar y setear la Company
        Company company = companyRepository.findById(ingredientDTO.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        ingredient.setCompany(company);

        // Verificar y setear la CategoryIngredient
        CategoryIngredient categoryIngredient = categoryIngredientRepository.findById(ingredientDTO.getCategoryIngredient().getId())
                .orElseThrow(() -> new RuntimeException("Category Ingredient not found"));
        ingredient.setCategoryIngredient(categoryIngredient);

        // Actualizar los campos restantes
        ingredient.setName(ingredientDTO.getName());
        ingredient.setPrice(ingredientDTO.getPrice());
        ingredient.setUnitMeasure(ingredientDTO.getUnitMeasure());
        ingredient.setStatus(ingredientDTO.isStatus());
        ingredient.setMinStock(ingredientDTO.getMinStock());
        ingredient.setCurrentStock(ingredientDTO.getCurrentStock());
        ingredient.setMaxStock(ingredientDTO.getMaxStock());

        // Guardar los cambios
        Ingredient updatedIngredient = ingredientRepository.save(ingredient);

        // Retornar el DTO actualizado
        return ingredientMapper.toDTO(updatedIngredient);
    }
}