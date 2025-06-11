package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.AddressDTO;
import com.example.buensabor.service.AddressService;

@RestController
@RequestMapping(path = "api/v1/addresses")
public class AddressController extends BaseControllerImplementation<AddressDTO, AddressService> {
    
}