package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.City;

@Repository
public interface CityRepository extends BaseRepository<City, Long> {
    
}