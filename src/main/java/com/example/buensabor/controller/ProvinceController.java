package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.ProvinceDTO;
import com.example.buensabor.service.ProvinceService;

@RestController
@RequestMapping(path = "public/api/v1/provinces")
public class ProvinceController extends BaseControllerImplementation<ProvinceDTO, ProvinceService> {
    
}