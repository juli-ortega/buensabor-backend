package com.example.buensabor.repository;

import com.example.buensabor.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // ...additional query methods if needed...
}
