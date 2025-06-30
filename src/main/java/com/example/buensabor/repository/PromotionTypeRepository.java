package com.example.buensabor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.PromotionType;

@Repository
public interface PromotionTypeRepository extends BaseRepository<PromotionType, Long> {
    List<PromotionType> findByCompanyId(Long companyId);
    Optional<PromotionType> findByNameAndCompany(String name, Company company);
    
}
