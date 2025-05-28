package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Country;
import com.example.buensabor.entity.dto.CountryDTO;
import com.example.buensabor.entity.mappers.CountryMapper;
import com.example.buensabor.repository.CountryRepository;
import com.example.buensabor.service.interfaces.ICountryService;

@Service
public class CountryService extends BaseServiceImplementation<CountryDTO, Country, Long> implements ICountryService {

    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        super(countryRepository, countryMapper);
    }
}
