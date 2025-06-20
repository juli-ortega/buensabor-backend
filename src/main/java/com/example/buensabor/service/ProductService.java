package com.example.buensabor.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.buensabor.Auth.CustomUserDetails;
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

    public List<ProductDTO> findByCompany(Long companyId) throws Exception {

        // Verificar que exista
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        // Buscar productos de esa company
        List<Product> products = productRepository.findByCompanyId(company.getId());

        // Mapear a DTOs
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        // Cargar ingredientes para cada producto
        for (ProductDTO productDTO : productDTOs) {
            List<ProductIngredientDTO> ingredients = productIngredientRepository
                    .findByProductId(productDTO.getId())
                    .stream()
                    .map(productIngredientMapper::toDTO)
                    .collect(Collectors.toList());

            productDTO.setProductIngredients(ingredients);
        }

        return productDTOs;
    }

    @Override
    @Transactional
    public ProductDTO save(ProductDTO productDTO) throws Exception {
        // Verificar que la Company existe
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Company company = companyRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        Category category = categoryRepository.findById(productDTO.getCategory().getId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

        // Crear y guardar el producto base
        Product product = productMapper.toEntity(productDTO);
        product.setCompany(company);
        product.setCategory(category);

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

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) throws Exception {
        // Buscar el producto a actualizar
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Company company = companyRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        Category category = categoryRepository.findById(productDTO.getCategory().getId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

        // Actualizar los datos del producto
        product.setCompany(company);
        product.setCategory(category);
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setEstimatedTime(productDTO.getEstimatedTime());
        product.setPrice(productDTO.getPrice());
        product.setImage(productDTO.getImage());

        // Guardar el producto actualizado
        Product updatedProduct = productRepository.save(product);

        // Eliminar relaciones anteriores de ingredientes
        productIngredientRepository.deleteByProductId(updatedProduct.getId());

        // Crear las nuevas relaciones con los ingredientes
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
