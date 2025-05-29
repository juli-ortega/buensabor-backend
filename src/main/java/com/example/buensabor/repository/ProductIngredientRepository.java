package com.example.buensabor.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.ProductIngredient;

@Repository
public interface ProductIngredientRepository extends BaseRepository<ProductIngredient, Long> {
    List<ProductIngredient> findByProductId(Long productId);
    void deleteByProductId(Long productId);

}