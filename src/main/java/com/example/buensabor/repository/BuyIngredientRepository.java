package com.example.buensabor.repository;

import com.example.buensabor.entity.BuyIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyIngredientRepository extends JpaRepository<BuyIngredient, Long> {
    // ...additional query methods if needed...
}
