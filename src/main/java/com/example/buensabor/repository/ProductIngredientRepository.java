package com.example.buensabor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.ProductIngredient;

import jakarta.transaction.Transactional;

@Repository
public interface ProductIngredientRepository extends BaseRepository<ProductIngredient, Long> {
    List<ProductIngredient> findByProductId(Long productId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductIngredient pi WHERE pi.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);

}