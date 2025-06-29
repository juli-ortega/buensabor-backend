package com.example.buensabor.repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Promotion;

import java.time.DayOfWeek;
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
        SELECT p FROM Promotion p
        JOIN ProductPromotion pp ON pp.promotion = p
        WHERE pp.product.id = :productId
        AND p.company.id = :companyId
        AND :currentDate BETWEEN p.dateFrom AND p.dateTo
        AND :currentDay MEMBER OF p.dayOfWeeks
        AND :currentTime BETWEEN p.timeFrom AND p.timeTo
    """)
    List<Promotion> findApplicablePromotionsForProduct(
        @Param("productId") Long productId,
        @Param("companyId") Long companyId,
        @Param("currentDate") LocalDate currentDate,
        @Param("currentDay") DayOfWeek currentDay,
        @Param("currentTime") LocalTime currentTime
    );


}
