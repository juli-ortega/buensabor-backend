package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Ingredient;

@Repository
public interface IngredientRepository extends BaseRepository<Ingredient, Long> {
    
}