package com.example.buensabor.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;
import com.example.buensabor.service.CategoryIngredientService;

@RestController
@PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
@RequestMapping(path = "api/v1/category-ingredients")
public class CategoryIngredientController extends BaseControllerImplementation<CategoryIngredientDTO, CategoryIngredientService> {
    
}
