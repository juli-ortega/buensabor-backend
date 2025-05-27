package com.example.buensabor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Product;
import com.example.buensabor.entity.dto.ProductDTO;
import com.example.buensabor.service.interfaces.IProductService;


@Service
public class ProductService extends BaseServiceImplementation<ProductDTO,Product, Long> implements IProductService {

    @Autowired
    public ProductService(BaseRepository<Product, Long> productRepository) {
        this.baseRepository = productRepository;
    }
}
