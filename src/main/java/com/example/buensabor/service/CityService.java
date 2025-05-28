package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.dto.CityDTO;
import com.example.buensabor.entity.mappers.CityMapper;
import com.example.buensabor.repository.CityRepository;
import com.example.buensabor.service.interfaces.ICityService;

@Service
public class CityService extends BaseServiceImplementation<CityDTO, City, Long> implements ICityService {

    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        super(cityRepository, cityMapper);
    }
}
