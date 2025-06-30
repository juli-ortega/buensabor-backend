package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.CityDTO;
import com.example.buensabor.service.CityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "public/api/v1/cities")
public class CityController extends BaseControllerImplementation<CityDTO, CityService> {

    @Autowired
    private CityService cityService;

    @GetMapping("/province/{provinceId}")
    public ResponseEntity<?> getCitiesByProvince(@PathVariable Long provinceId) {
        try {
            List<CityDTO> cities = cityService.getCitiesByProvinceId(provinceId);
            return ResponseEntity.ok(cities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: Error al obtener las cities, " + e.getMessage());        }
    }
  
}