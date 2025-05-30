package com.example.buensabor.repository;

import com.example.buensabor.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // ...additional query methods if needed...
}
