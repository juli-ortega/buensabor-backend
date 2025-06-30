package com.example.buensabor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.CategoryDTO;
import com.example.buensabor.service.CategoryService;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController extends BaseControllerImplementation<CategoryDTO, CategoryService> {

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener las categorías: " + e.getMessage());
        }
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<?> getAllPublicCategoryByCompany(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findAllByCompanyId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener las categorías: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener la categoría: " + e.getMessage());
        }
    }
}