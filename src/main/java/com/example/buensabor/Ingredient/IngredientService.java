package com.example.buensabor.Ingredient;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.Ingredient.Interfaces.IIngredientService;

@Service
public class IngredientService extends BaseServiceImplementation<Ingredient, Long> implements IIngredientService {

    public IngredientService(BaseRepository<Ingredient, Long> ingredientRepository) {
        this.baseRepository = ingredientRepository;
    }
}
