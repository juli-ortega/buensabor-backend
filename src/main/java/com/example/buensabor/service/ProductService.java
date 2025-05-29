package com.example.buensabor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.ProductIngredient;
import com.example.buensabor.entity.dto.ProductDTO;
import com.example.buensabor.entity.dto.ProductIngredientDTO;
import com.example.buensabor.entity.mappers.ProductIngredientMapper;
import com.example.buensabor.entity.mappers.ProductMapper;
import com.example.buensabor.repository.CategoryRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.IngredientRepository;
import com.example.buensabor.repository.ProductIngredientRepository;
import com.example.buensabor.repository.ProductRepository;
import com.example.buensabor.service.interfaces.IProductService;

import jakarta.transaction.Transactional;

@Service
public class ProductService extends BaseServiceImplementation<ProductDTO, Product, Long> implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final IngredientRepository ingredientRepository;
    private final ProductIngredientRepository productIngredientRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;
    private final ProductIngredientMapper productIngredientMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper,IngredientRepository ingredientRepository,ProductIngredientRepository productIngredientRepository, CompanyRepository companyRepository, CategoryRepository categoryRepository, ProductIngredientMapper productIngredientMapper) {
        super(productRepository, productMapper);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.ingredientRepository = ingredientRepository;
        this.productIngredientRepository = productIngredientRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.productIngredientMapper = productIngredientMapper;
    }

    @Override
    @Transactional
    public ProductDTO save(ProductDTO productDTO) throws Exception {
        // Validar la empresa
        Company company = companyRepository.findById(productDTO.getCompany().getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        Category category = categoryRepository.findById(productDTO.getCategory().getId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

        // Crear y guardar el producto base
        Product product = new Product();
        product.setCompany(company);
        product.setCategory(category);
        product.setDescription(productDTO.getDescription());
        product.setTitle(productDTO.getTitle());
        product.setEstimatedTime(productDTO.getEstimatedTime());
        product.setPrice(productDTO.getPrice());
        product.setImage(productDTO.getImage());

        Product savedProduct = productRepository.save(product);

        // Crear las relaciones con los ingredientes
        for (ProductIngredientDTO pidto : productDTO.getProductIngredients()) {
            Ingredient ingredient = ingredientRepository.findById(pidto.getIngredient().getId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

            ProductIngredient pi = new ProductIngredient();
            pi.setProduct(savedProduct);
            pi.setIngredient(ingredient);
            pi.setQuantity(pidto.getQuantity());

            productIngredientRepository.save(pi);
        }

        ProductDTO dto = productMapper.toDTO(savedProduct);

        List<ProductIngredientDTO> ingredients = productIngredientRepository
            .findByProductId(savedProduct.getId())
            .stream()
            .map(productIngredientMapper::toDTO)
            .collect(Collectors.toList());

        dto.setProductIngredients(ingredients);

        return dto;
    }
}
