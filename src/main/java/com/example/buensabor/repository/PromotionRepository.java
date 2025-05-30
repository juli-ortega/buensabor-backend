package com.example.buensabor.repository;

import com.example.buensabor.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    // ...additional query methods if needed...
}
