package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.dto.ProductDTO;
import com.example.buensabor.entity.mappers.ProductMapper;
import com.example.buensabor.repository.ProductRepository;
import com.example.buensabor.service.interfaces.IProductService;

@Service
public class ProductService extends BaseServiceImplementation<ProductDTO,Product, Long> implements IProductService {

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
       super(productRepository, productMapper);
    }
}
