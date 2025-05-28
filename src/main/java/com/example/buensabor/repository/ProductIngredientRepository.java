package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.ProductIngredient;

@Repository
public interface ProductIngredientRepository extends BaseRepository<ProductIngredient, Long> {
    
}