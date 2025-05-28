package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.entity.mappers.IngredientMapper;
import com.example.buensabor.repository.IngredientRepository;
import com.example.buensabor.service.interfaces.IIngredientService;

@Service
public class IngredientService extends BaseServiceImplementation<IngredientDTO,Ingredient, Long> implements IIngredientService {

    public IngredientService(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper) {
        super(ingredientRepository, ingredientMapper);
    }
}
