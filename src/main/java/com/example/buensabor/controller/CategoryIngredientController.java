package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.CategoryIngredientDTO;
import com.example.buensabor.service.CategoryIngredientService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/category-ingredients")
public class CategoryIngredientController extends BaseControllerImplementation<CategoryIngredientDTO, CategoryIngredientService> {
    
}
