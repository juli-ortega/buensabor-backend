package com.example.buensabor.Product;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    
}