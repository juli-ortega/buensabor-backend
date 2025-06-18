package com.example.buensabor.repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Promotion;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends BaseRepository<Promotion, Long> {
    List<Promotion> findByCompany(Company company);
    Optional<Promotion> findByIdAndCompany(Long id, Company company);
}
