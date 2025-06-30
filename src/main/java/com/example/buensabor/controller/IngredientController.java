package com.example.buensabor.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.entity.dto.CreateDTOs.IngredientCreateDTO;
import com.example.buensabor.service.IngredientService;
import com.example.buensabor.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController extends BaseControllerImplementation<IngredientDTO, IngredientService> {

    @Autowired
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @GetMapping("/nottoprepare")
    public ResponseEntity<?> getNotToPrepareByCompany() {
        try {
            return ResponseEntity.ok(service.getNotToPrepareByCompany());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @GetMapping("/toprepare")
    public ResponseEntity<?> getToPrepareByCompany() {
        try {
            return ResponseEntity.ok(service.getToPrepareByCompany());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(service.getAllByCompany());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createIngredient(
            @RequestPart("ingredient") String ingredientString,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            // Validar que el JSON no esté vacío
            if (ingredientString == null || ingredientString.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El campo 'ingredient' no puede estar vacío.");
            }

            // Parsear JSON a DTO
            IngredientCreateDTO ingredientDTO = objectMapper.readValue(ingredientString, IngredientCreateDTO.class);

            String imageUrl = null;
            if (imageFile != null && !imageFile.isEmpty()) {
                imageUrl = productService.saveImage(imageFile);
            }

            IngredientDTO savedIngredient = service.save(ingredientDTO, imageUrl);


            return ResponseEntity.ok(savedIngredient);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al parsear el ingrediente: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }


}