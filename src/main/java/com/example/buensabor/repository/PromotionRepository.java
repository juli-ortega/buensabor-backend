package com.example.buensabor.repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Promotion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends BaseRepository<Promotion, Long> {
    List<Promotion> findByCompany(Company company);
    Optional<Promotion> findByIdAndCompany(Long id, Company company);

    @Query("""
        SELECT pp.promotion FROM ProductPromotion pp
        WHERE pp.product.id = :productId
        AND pp.promotion.company.id = :companyId
        AND pp.promotion.dateFrom <= :currentDate
        AND pp.promotion.dateTo >= :currentDate
    """)
    List<Promotion> findValidPromotionsForProduct(
        @Param("productId") Long productId,
        @Param("companyId") Long companyId,
        @Param("currentDate") LocalDate currentDate
    );


}
