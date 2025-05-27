package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Ingredient;
import com.example.buensabor.service.interfaces.IIngredientService;

@Service
public class IngredientService extends BaseServiceImplementation<Ingredient, Long> implements IIngredientService {

    public IngredientService(BaseRepository<Ingredient, Long> ingredientRepository) {
        this.baseRepository = ingredientRepository;
    }
}
