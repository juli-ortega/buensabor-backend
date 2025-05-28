package com.example.buensabor.Seeders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;

import com.example.buensabor.repository.CountryRepository;
import com.example.buensabor.repository.ProvinceRepository;
import com.example.buensabor.repository.CityRepository;
import com.example.buensabor.entity.Country;
import com.example.buensabor.entity.Province;
import com.example.buensabor.entity.dto.CityDTO;
import com.example.buensabor.entity.dto.CountryDTO;
import com.example.buensabor.entity.dto.ProvinceDTO;
import com.example.buensabor.entity.City;

import java.io.InputStream;
import java.util.List;

@Component
public class LocationSeedData implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;

    public LocationSeedData(CountryRepository countryRepository, ProvinceRepository provinceRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.provinceRepository = provinceRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
    
        // Leer países
        InputStream countryStream = getClass().getResourceAsStream("/data/countries.json");
        if (countryStream == null) {
            throw new IllegalArgumentException("No se pudo encontrar el archivo countries.json");
        }
        List<CountryDTO> countryDTOs = mapper.readValue(countryStream, new TypeReference<List<CountryDTO>>() {});
        List<Country> countries = countryDTOs.stream().map(dto -> {
            Country country = new Country();
            country.setName(dto.getName());
            return country;
        }).collect(Collectors.toList());
    
        // Filtrar duplicados antes de guardar
        Set<String> existingCountryNames = countryRepository.findAll().stream()
            .map(Country::getName)
            .collect(Collectors.toSet());
        countries.removeIf(country -> existingCountryNames.contains(country.getName()));
        countryRepository.saveAll(countries);
    
        // Mapear países por nombre para asignar referencias
        Map<String, Country> countryMap = countryRepository.findAll().stream()
            .collect(Collectors.toMap(
                Country::getName,
                country -> country,
                (existing, duplicate) -> existing // Manejar duplicados: conservar el existente
            ));
    
        // Leer provincias
        InputStream provinceStream = getClass().getResourceAsStream("/data/provinces.json");
        if (provinceStream == null) {
            throw new IllegalArgumentException("No se pudo encontrar el archivo provinces.json");
        }
        List<ProvinceDTO> provinceDTOs = mapper.readValue(provinceStream, new TypeReference<List<ProvinceDTO>>() {});
        List<Province> provinces = provinceDTOs.stream().map(dto -> {
            Province province = new Province();
            province.setName(dto.getName());
            province.setCountry(countryMap.get(dto.getCountry().getName()));
            return province;
        }).collect(Collectors.toList());
    
        // Filtrar duplicados antes de guardar
        Set<String> existingProvinceNames = provinceRepository.findAll().stream()
            .map(Province::getName)
            .collect(Collectors.toSet());
        provinces.removeIf(province -> existingProvinceNames.contains(province.getName()));
        provinceRepository.saveAll(provinces);
    
        // Mapear provincias por nombre para asignar referencias
        Map<String, Province> provinceMap = provinceRepository.findAll().stream()
            .collect(Collectors.toMap(
                Province::getName,
                province -> province,
                (existing, duplicate) -> existing // Manejar duplicados: conservar el existente
            ));
    
        // Leer ciudades
        InputStream cityStream = getClass().getResourceAsStream("/data/cities.json");
        if (cityStream == null) {
            throw new IllegalArgumentException("No se pudo encontrar el archivo cities.json");
        }
        List<CityDTO> cityDTOs = mapper.readValue(cityStream, new TypeReference<List<CityDTO>>() {});
        List<City> cities = cityDTOs.stream().map(dto -> {
            City city = new City();
            city.setName(dto.getName());
            city.setProvince(provinceMap.get(dto.getProvince().getName()));
            return city;
        }).collect(Collectors.toList());
    
        // Filtrar duplicados antes de guardar
        Set<String> existingCityNames = cityRepository.findAll().stream()
            .map(City::getName)
            .collect(Collectors.toSet());
        cities.removeIf(city -> existingCityNames.contains(city.getName()));
        cityRepository.saveAll(cities);
    }
}