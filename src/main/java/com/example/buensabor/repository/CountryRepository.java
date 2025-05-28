package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Country;

@Repository
public interface CountryRepository extends BaseRepository<Country, Long> {
    
}