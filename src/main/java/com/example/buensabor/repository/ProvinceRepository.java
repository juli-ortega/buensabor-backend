package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Province;

@Repository
public interface ProvinceRepository extends BaseRepository<Province, Long> {
    
}