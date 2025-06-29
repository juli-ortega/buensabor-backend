package com.example.buensabor.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Ingredient;

@Repository
public interface IngredientRepository extends BaseRepository<Ingredient, Long> {
    List<Ingredient> findByCompanyId(Long companyId);
    List<Ingredient> findByCompanyIdAndIsToPrepareTrue(Long companyId);
    List<Ingredient> findByCompanyIdAndIsToPrepareFalse(Long companyId);
}