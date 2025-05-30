package com.example.buensabor.repository;

import com.example.buensabor.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    // ...additional query methods if needed...
}
