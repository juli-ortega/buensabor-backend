package com.example.buensabor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.ProductIngredient;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.entity.mappers.IngredientMapper;
import com.example.buensabor.repository.CategoryIngredientRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.IngredientRepository;
import com.example.buensabor.repository.ProductIngredientRepository;
import com.example.buensabor.repository.ProductRepository;
import com.example.buensabor.service.interfaces.IIngredientService;

import jakarta.transaction.Transactional;





import com.example.buensabor.entity.dto.ResponseDTOs.IngredientResponseDTO;
import com.example.buensabor.entity.mappers.IngredientMapperExt;


@Service
public class IngredientService extends BaseServiceImplementation<IngredientDTO,Ingredient, Long> implements IIngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final IngredientMapperExt ingredientMapperExt;
    private final CompanyRepository companyRepository;
    private final CategoryIngredientRepository categoryIngredientRepository;
    private final ProductIngredientRepository productIngredientRepository;
    private final ProductRepository productRepository;

    public IngredientService(
        IngredientRepository ingredientRepository,
        IngredientMapper ingredientMapper,
        IngredientMapperExt ingredientMapperExt,
        CompanyRepository companyRepository,
        ProductRepository productRepository,
        ProductIngredientRepository productIngredientRepository,
        CategoryIngredientRepository categoryIngredientRepository
    ){
        super(ingredientRepository, ingredientMapper);
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.ingredientMapperExt = ingredientMapperExt;
        this.companyRepository = companyRepository;
        this.categoryIngredientRepository = categoryIngredientRepository;
        this.productIngredientRepository = productIngredientRepository;
        this.productRepository = productRepository;
    }

    public List<IngredientResponseDTO> getNotToPrepareByCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ingredient> ingredients = ingredientRepository.findByCompanyIdAndIsToPrepareFalse(userDetails.getId());
        return ingredients.stream()
                .map(ingredientMapperExt::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IngredientResponseDTO> getToPrepareByCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ingredient> ingredients = ingredientRepository.findByCompanyIdAndIsToPrepareTrue(userDetails.getId());
        return ingredients.stream()
                .map(ingredientMapperExt::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IngredientResponseDTO> getAllByCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ingredient> ingredients = ingredientRepository.findByCompanyId(userDetails.getId());
        return ingredients.stream()
                .map(ingredientMapperExt::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public IngredientDTO save(IngredientDTO ingredientDTO) throws Exception {

        // Verificar que la Company existe
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Company company = companyRepository.findById(userDetails.getId())
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

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Company company = companyRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        ingredient.setCompany(company);

        // Verificar y setear la CategoryIngredient
        CategoryIngredient categoryIngredient = categoryIngredientRepository.findById(ingredientDTO.getCategoryIngredient().getId())
                .orElseThrow(() -> new RuntimeException("Category Ingredient not found"));
        ingredient.setCategoryIngredient(categoryIngredient);

        // Guardar precio anterior para comparación
        double previousPrice = ingredient.getPrice();

        // Actualizar los campos restantes
        ingredient.setName(ingredientDTO.getName());
        ingredient.setPrice(ingredientDTO.getPrice());
        ingredient.setUnitMeasure(ingredientDTO.getUnitMeasure());
        ingredient.setStatus(ingredientDTO.isStatus());
        ingredient.setMinStock(ingredientDTO.getMinStock());
        ingredient.setCurrentStock(ingredientDTO.getCurrentStock());
        ingredient.setMaxStock(ingredientDTO.getMaxStock());

        // Guardar los cambios del ingrediente
        Ingredient updatedIngredient = ingredientRepository.save(ingredient);

        // Si cambió el precio, actualizar productos relacionados
        if (previousPrice != ingredientDTO.getPrice()) {
            List<ProductIngredient> productIngredients = productIngredientRepository.findByIngredient(ingredient);
            for (ProductIngredient pi : productIngredients) {
                Product product = pi.getProduct();
                // Calcular nuevo precio del producto
                double totalPrice = 0.0;
                for (ProductIngredient ingredientInProduct : product.getProductIngredients()) {
                    double ingredientPrice = ingredientInProduct.getIngredient().getPrice();
                    double quantity = ingredientInProduct.getQuantity();
                    totalPrice += ingredientPrice * quantity;
                }
                product.setPrice(totalPrice);
                productRepository.save(product);
            }
        }

        return ingredientMapper.toDTO(updatedIngredient);
    }

}