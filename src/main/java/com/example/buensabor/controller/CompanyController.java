package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.service.CompanyService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/company")
public class CompanyController extends BaseControllerImplementation<CompanyDTO, CompanyService> {
    
}
