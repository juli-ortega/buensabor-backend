package com.example.buensabor.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.ProductDTO;
import com.example.buensabor.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/products")
@AllArgsConstructor
public class ProductController extends BaseControllerImplementation<ProductDTO, ProductService>  {

    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;  

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    public ResponseEntity<?> create(
            @RequestPart("product") String productString,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            // Validar que el JSON no esté vacío
            if (productString == null || productString.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El campo 'product' no puede estar vacío.");
            }

            // Parsear el JSON a DTO
            ProductDTO productDTO = objectMapper.readValue(productString, ProductDTO.class);

            // Procesar imagen si viene
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = productService.saveImage(imageFile);
                productDTO.setImage(imageUrl);
            }

            // Guardar producto
            return super.save(productDTO);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al parsear el producto: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN', 'COMPANY')") // Permitir acceso solo al rol ADMIN
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener los productos: " + e.getMessage());
        }
    }


}
