package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Category;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
}
