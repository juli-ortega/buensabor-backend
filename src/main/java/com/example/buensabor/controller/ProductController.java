package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.ProductDTO;
import com.example.buensabor.service.ProductService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/products")
public class ProductController extends BaseControllerImplementation<ProductDTO, ProductService>  {
    
}
