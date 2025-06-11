package com.example.buensabor.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.City;

@Repository
public interface CityRepository extends BaseRepository<City, Long> {
    List<City> findByProvinceId(Long provinceId);
}