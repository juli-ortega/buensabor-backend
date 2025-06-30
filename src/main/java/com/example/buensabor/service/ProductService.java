package com.example.buensabor.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.buensabor.Auth.AuthService;
import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.ProductIngredient;
import com.example.buensabor.entity.ProductPromotion;
import com.example.buensabor.entity.Promotion;
import com.example.buensabor.entity.dto.CategoryDTO;
import com.example.buensabor.entity.dto.ProductDTO;
import com.example.buensabor.entity.dto.ProductIngredientDTO;
import com.example.buensabor.entity.mappers.ProductIngredientMapper;
import com.example.buensabor.entity.mappers.ProductMapper;
import com.example.buensabor.repository.CategoryRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.IngredientRepository;
import com.example.buensabor.repository.ProductIngredientRepository;
import com.example.buensabor.repository.ProductPromotionRepository;
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
    private final PromotionService promotionService;
    private final ProductPromotionRepository productPromotionRepository;
    private final AuthService authService;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, AuthService authService, ProductPromotionRepository productPromotionRepository, PromotionService promotionService, IngredientRepository ingredientRepository,ProductIngredientRepository productIngredientRepository, CompanyRepository companyRepository, CategoryRepository categoryRepository, ProductIngredientMapper productIngredientMapper) {
        super(productRepository, productMapper);
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.ingredientRepository = ingredientRepository;
        this.productIngredientRepository = productIngredientRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
        this.productIngredientMapper = productIngredientMapper;
        this.promotionService = promotionService;
        this.productPromotionRepository = productPromotionRepository;
        this.authService = authService;
    }

    @Override
    public List<ProductDTO> findAll() throws Exception {
        List<ProductDTO> products = super.findAll();

        for (ProductDTO productDTO : products) {
            List<ProductIngredientDTO> ingredients = productIngredientRepository
                .findByProductId(productDTO.getId())
                .stream()
                .map(productIngredientMapper::toDTO)
                .collect(Collectors.toList());
            
            productDTO.setProductIngredients(ingredients);
        }

        return products;
    }

    @Override
    public ProductDTO findById(Long id) throws Exception {
        ProductDTO productDTO = super.findById(id); // obtiene producto mapeado sin ingredientes

        // Obtener ingredientes asociados
        List<ProductIngredientDTO> ingredients = productIngredientRepository
            .findByProductId(id)
            .stream()
            .map(productIngredientMapper::toDTO)
            .collect(Collectors.toList());

        productDTO.setProductIngredients(ingredients);

        return productDTO;
    }

    public List<ProductDTO> findByLoggedCompany() throws Exception {
        Company company = authService.getLoggedCompany();
        return getProductsForCompany(company);
    }

    // Público para company por ID (por parámetro)
    public List<ProductDTO> findByCompany(Long companyId) throws Exception {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return getProductsForCompany(company);
    }

    // Método privado reutilizable
    private List<ProductDTO> getProductsForCompany(Company company) {
        List<Product> products = productRepository.findByCompanyId(company.getId());
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            ProductDTO productDTO = productMapper.toDTO(product);

            // Setear imagen del producto
            productDTO.setImage(product.getImage());

            // Cargar ingredientes
            List<ProductIngredientDTO> ingredients = productIngredientRepository
                    .findByProductId(product.getId())
                    .stream()
                    .map(productIngredientMapper::toDTO)
                    .collect(Collectors.toList());
            productDTO.setProductIngredients(ingredients);

            // Cargar promociones si existen
            Optional<Promotion> optionalPromotion = promotionService.getApplicablePromotion(product, company);

            if (optionalPromotion.isPresent()) {
                Promotion promotion = optionalPromotion.get();
                Optional<ProductPromotion> productPromotionOpt = productPromotionRepository
                        .findByProductAndPromotion(product.getId(), promotion.getId());

                if (productPromotionOpt.isPresent()) {
                    ProductPromotion productPromotion = productPromotionOpt.get();
                    productDTO.setPromotionalPrice(productPromotion.getValue());
                    productDTO.setPromotionDescription(promotion.getDiscountDescription());
                } else {
                    productDTO.setPromotionalPrice(null);
                    productDTO.setPromotionDescription(promotion.getDiscountDescription());
                }
            } else {
                productDTO.setPromotionalPrice(null);
                productDTO.setPromotionDescription(null);
            }

            // Armar categoría con su imagen, categoría padre y company id
            Category category = product.getCategory();
            if (category != null) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());

                // Setear company id dentro de BaseDTO
                if (category.getCompany() != null) {
                    BaseDTO companyDTO = new BaseDTO();
                    companyDTO.setId(category.getCompany().getId());
                    categoryDTO.setCompany(companyDTO);
                }

                if (category.getParent() != null) {
                    Category parent = category.getParent();
                    CategoryDTO parentDTO = new CategoryDTO();
                    parentDTO.setId(parent.getId());
                    parentDTO.setName(parent.getName());

                    // También podés agregar company id al padre si querés:
                    if (parent.getCompany() != null) {
                        BaseDTO parentCompanyDTO = new BaseDTO();
                        parentCompanyDTO.setId(parent.getCompany().getId());
                        parentDTO.setCompany(parentCompanyDTO);
                    }

                    categoryDTO.setParent(parentDTO);
                }

                productDTO.setCategory(categoryDTO);
            }

            productDTOs.add(productDTO);
        }

        return productDTOs;
    }



    @Override
    @Transactional
    public ProductDTO save(ProductDTO productDTO) throws Exception {
        // Verificar que la Company existe
        Company company = authService.getLoggedCompany();

        // Buscar categoría
        Category category = categoryRepository.findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Crear y guardar el producto base
        Product product = productMapper.toEntity(productDTO);
        product.setCompany(company);
        product.setCategory(category);

        // Inicializar la lista de ProductIngredients
        List<ProductIngredient> productIngredients = new ArrayList<>();

        // Crear las relaciones con los ingredientes
        for (ProductIngredientDTO pidto : productDTO.getProductIngredients()) {
            Ingredient ingredient = ingredientRepository.findById(pidto.getIngredient().getId())
                    .orElseThrow(() -> new RuntimeException("Ingredient not found"));

            ProductIngredient pi = new ProductIngredient();
            pi.setProduct(product);  // Importante: se referencia al producto antes de persistir
            pi.setIngredient(ingredient);
            pi.setQuantity(pidto.getQuantity());

            productIngredients.add(pi);
        }

        // Asignar las relaciones al producto
        product.setProductIngredients(productIngredients);

        // Guardar todo junto (gracias a CascadeType.ALL en Product.productIngredients)
        Product savedProduct = productRepository.save(product);

        // Retornar DTO
        ProductDTO dto = productMapper.toDTO(savedProduct);

        List<ProductIngredientDTO> ingredients = savedProduct.getProductIngredients()
                .stream()
                .map(productIngredientMapper::toDTO)
                .collect(Collectors.toList());

        dto.setProductIngredients(ingredients);

        return dto;
    }

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) throws Exception {
        // Buscar el producto a actualizar
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        Company company = authService.getLoggedCompany();

        Category category = categoryRepository.findById(productDTO.getCategory().getId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

        // Actualizar los datos del producto
        product.setCompany(company);
        product.setCategory(category);
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setEstimatedTime(productDTO.getEstimatedTime());
        product.setProfit_percentage(productDTO.getProfit_percentage());
        product.setPrice(productDTO.getPrice());

        // Solo actualiza la imagen si viene una nueva en el DTO
        if (productDTO.getImage() != null && !productDTO.getImage().isEmpty()) {
            product.setImage(productDTO.getImage());
        }

        // Guardar producto actualizado
        Product updatedProduct = productRepository.save(product);

        // Eliminar relaciones anteriores de ingredientes
        productIngredientRepository.deleteByProductId(updatedProduct.getId());

        // Crear nuevas relaciones con los ingredientes
        for (ProductIngredientDTO pidto : productDTO.getProductIngredients()) {
            Ingredient ingredient = ingredientRepository.findById(pidto.getIngredient().getId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

            ProductIngredient pi = new ProductIngredient();
            pi.setProduct(updatedProduct);
            pi.setIngredient(ingredient);
            pi.setQuantity(pidto.getQuantity());

            productIngredientRepository.save(pi);
        }

        // Mapear a DTO y devolver
        ProductDTO dto = productMapper.toDTO(updatedProduct);

        List<ProductIngredientDTO> ingredients = productIngredientRepository
            .findByProductId(updatedProduct.getId())
            .stream()
            .map(productIngredientMapper::toDTO)
            .collect(Collectors.toList());

        dto.setProductIngredients(ingredients);

        return dto;
    }


    public String saveImage(MultipartFile file) throws IOException {
        // Límite de tamaño: 5 MB (5 * 1024 * 1024 bytes)
        long maxSize = 5 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new IOException("El archivo supera el tamaño máximo permitido de 5 MB.");
        }

        // Leer los primeros 8 bytes para verificar magic bytes
        byte[] header = new byte[8];
        InputStream is = file.getInputStream();
        is.read(header);
        if (!isImage(header)) {
            throw new IOException("El archivo no es una imagen válida.");
        }

        // Renombrar el archivo usando UUID para hacerlo único
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        // Guardar imagen en uploads/
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = uploadDir + uniqueFilename;
        file.transferTo(new File(filePath));

        // Devolver URL pública
        return "http://localhost:8080/uploads/" + uniqueFilename;
    }

    // Validación de magic bytes
    private boolean isImage(byte[] header) {
        // JPG/JPEG
        if (header[0] == (byte) 0xFF && header[1] == (byte) 0xD8) {
            return true;
        }
        // PNG
        if (header[0] == (byte) 0x89 && header[1] == (byte) 0x50 &&
                header[2] == (byte) 0x4E && header[3] == (byte) 0x47) {
            return true;
        }
        // GIF
        if (header[0] == (byte) 0x47 && header[1] == (byte) 0x49 &&
                header[2] == (byte) 0x46) {
            return true;
        }
        // BMP
        if (header[0] == (byte) 0x42 && header[1] == (byte) 0x4D) {
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        // Buscar el producto a eliminar
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Eliminar las relaciones de ingredientes
        productIngredientRepository.deleteByProductId(product.getId());

        // Eliminar el producto
        productRepository.delete(product);

        return true;
    }


}
