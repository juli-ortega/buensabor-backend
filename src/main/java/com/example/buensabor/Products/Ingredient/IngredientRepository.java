package com.example.buensabor.Products.Ingredient;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;

@Repository
public interface IngredientRepository extends BaseRepository<Ingredient, Long> {
    
}