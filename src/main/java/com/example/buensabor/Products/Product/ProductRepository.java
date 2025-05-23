package com.example.buensabor.Products.Product;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    
}