package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.CategoryIngredient;

@Repository
public interface CategoryIngredientRepository extends BaseRepository<CategoryIngredient, Long> {
    
}
