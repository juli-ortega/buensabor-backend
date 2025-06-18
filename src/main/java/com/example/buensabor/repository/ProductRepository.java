package com.example.buensabor.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Product;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    List<Product> findByCompanyId(Long companyId);
}