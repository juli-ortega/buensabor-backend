package com.example.buensabor.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Payment;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
    Optional<Payment> findByMercadoPagoId(String mercadoPagoId);
    Optional<Payment> findByOrderId(Long orderId);
}