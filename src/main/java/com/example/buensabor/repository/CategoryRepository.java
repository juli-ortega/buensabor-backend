package com.example.buensabor.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Category;
import com.example.buensabor.entity.Company;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
    Optional<Category> findByNameAndCompany(String name, Company company);
}
