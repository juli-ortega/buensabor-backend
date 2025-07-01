package com.example.buensabor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;
import com.example.buensabor.service.CategoryIngredientService;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'COMPANY', 'EMPLOYEE')")
@RequestMapping(path = "api/v1/category-ingredients")
public class CategoryIngredientController extends BaseControllerImplementation<CategoryIngredientDTO, CategoryIngredientService> {
    
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener las categorías de ingredientes: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener la categoría de ingrediente: " + e.getMessage());
        }
    }
}
