package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.CityDTO;
import com.example.buensabor.service.CityService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/cities")
public class CityController extends BaseControllerImplementation<CityDTO, CityService> {
    
}