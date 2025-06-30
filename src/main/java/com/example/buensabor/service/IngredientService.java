package com.example.buensabor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.ProductIngredient;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.entity.dto.CreateDTOs.IngredientCreateDTO;
import com.example.buensabor.entity.mappers.IngredientMapper;
import com.example.buensabor.repository.CategoryIngredientRepository;
import com.example.buensabor.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public IngredientService(
        IngredientRepository ingredientRepository,
        IngredientMapper ingredientMapper,
        IngredientMapperExt ingredientMapperExt,
        CompanyRepository companyRepository,
        ProductRepository productRepository,
        CategoryRepository categoryRepository,
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
        this.categoryRepository = categoryRepository;
    }

    public List<IngredientResponseDTO> getNotToPrepareByCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ingredient> ingredients = ingredientRepository.findByCompanyIdAndIsToPrepareFalse(userDetails.getId());
        // Filtrar solo activos
        ingredients = ingredients.stream()
                .filter(i -> i.getIsActive() == null || i.getIsActive())
                .collect(Collectors.toList());
        return ingredients.stream()
                .map(ingredientMapperExt::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IngredientResponseDTO> getToPrepareByCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ingredient> ingredients = ingredientRepository.findByCompanyIdAndIsToPrepareTrue(userDetails.getId());
        // Filtrar solo activos
        ingredients = ingredients.stream()
                .filter(i -> i.getIsActive() == null || i.getIsActive())
                .collect(Collectors.toList());
        return ingredients.stream()
                .map(ingredientMapperExt::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<IngredientResponseDTO> getAllByCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ingredient> ingredients = ingredientRepository.findByCompanyId(userDetails.getId());
        // Filtrar solo activos
        ingredients = ingredients.stream()
                .filter(i -> i.getIsActive() == null || i.getIsActive())
                .collect(Collectors.toList());
        return ingredients.stream()
                .map(ingredientMapperExt::toResponseDTO)
                .collect(Collectors.toList());
    }

   @Transactional
    public IngredientDTO save(IngredientCreateDTO ingredientDTO, String productImageUrl) throws Exception {
        // Obtener la company logueada
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Company company = companyRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        // Verificar categoría de ingrediente
        CategoryIngredient categoryIngredient = categoryIngredientRepository.findById(ingredientDTO.getCategoryIngredient().getId())
            .orElseThrow(() -> new RuntimeException("Category Ingredient not found"));

        // Crear Ingredient
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDTO);
        ingredient.setCompany(company);
        ingredient.setCategoryIngredient(categoryIngredient);
        ingredientRepository.save(ingredient);

        // Si NO es para preparar, crear también un producto asociado
        if (!ingredient.isToPrepare()) {
            // Obtener categoría para el producto desde el DTO
            Category productCategory = categoryRepository.findById(ingredientDTO.getCategoryIdProduct())
                .orElseThrow(() -> new RuntimeException("Product category not found"));

            Product product = new Product();
            product.setCompany(company);
            product.setCategory(productCategory);
            product.setTitle(ingredient.getName());
            product.setDescription("Producto generado automáticamente desde ingrediente");
            product.setEstimatedTime(0);
            product.setPrice(ingredientDTO.getPriceProduct());
            product.setProfit_percentage(ingredientDTO.getProfit_percentage());
            product.setImage(productImageUrl); // 👉 setear imagen acá si vino

            // Relacionar el Product con el Ingredient
            ProductIngredient pi = new ProductIngredient();
            pi.setProduct(product);
            pi.setIngredient(ingredient);
            pi.setQuantity(1);

            List<ProductIngredient> productIngredients = new ArrayList<>();
            productIngredients.add(pi);
            product.setProductIngredients(productIngredients);

            productRepository.save(product);
        }

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
        ingredient.setToPrepare(ingredientDTO.isToPrepare());
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

                // Diferencia de precio del ingrediente
                double priceDifference = ingredientDTO.getPrice() - previousPrice;

                // Calcular aumento con porcentaje de ganancia
                double profitPercentage = product.getProfit_percentage() / 100.0;
                double increaseAmount = priceDifference * pi.getQuantity();
                double increaseWithProfit = increaseAmount + (increaseAmount * profitPercentage);

                // Sumar al precio actual del producto
                double newPrice = product.getPrice() + increaseWithProfit;
                product.setPrice(newPrice);

                productRepository.save(product);
            }
        }


        return ingredientMapper.toDTO(updatedIngredient);
    }

}