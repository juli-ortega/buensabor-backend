package com.example.buensabor.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Company;

@Repository
public interface CompanyRepository extends BaseRepository<Company, Long> {
    Optional<Company> findByEmail(String email);
}
