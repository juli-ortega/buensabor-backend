package com.example.buensabor.repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Order;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {
    List<Order> findByClientIdOrderByInitAtDesc(Long clientId);
    List<Order> findByCompanyId(Long companyId);
    
}
