package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.CountryDTO;
import com.example.buensabor.service.CountryService;

@RestController
@RequestMapping(path = "public/api/v1/countries")
public class CountryController extends BaseControllerImplementation<CountryDTO, CountryService>  {
    
}