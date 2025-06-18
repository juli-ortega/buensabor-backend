package com.example.buensabor.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.ProductPromotion;

@Repository
public interface ProductPromotionRepository extends BaseRepository<ProductPromotion, Long>{
    List<ProductPromotion> findByProductId(Long productId);
}
