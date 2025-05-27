package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.CategoryIngredient;
import com.example.buensabor.service.interfaces.ICategoryIngredientService;

@Service
public class CategoryIngredientService extends BaseServiceImplementation<CategoryIngredient, Long> implements ICategoryIngredientService{
    
}
