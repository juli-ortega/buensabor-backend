package com.example.buensabor.repository;

import com.example.buensabor.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    // ...additional query methods if needed...
}
