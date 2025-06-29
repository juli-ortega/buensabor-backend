package com.example.buensabor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.ProductPromotion;

@Repository
public interface ProductPromotionRepository extends BaseRepository<ProductPromotion, Long>{
    List<ProductPromotion> findByProductId(Long productId);

    @Query("""
        SELECT pp FROM ProductPromotion pp
        WHERE pp.product.id = :productId
        AND pp.promotion.id = :promotionId
    """)
    Optional<ProductPromotion> findByProductAndPromotion(
        @Param("productId") Long productId,
        @Param("promotionId") Long promotionId
    );
}
