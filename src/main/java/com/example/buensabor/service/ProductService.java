package com.example.buensabor.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.Product.Interfaces.IProductService;

@Service
public class ProductService extends BaseServiceImplementation<Product, Long> implements IProductService {

    @Autowired
    public ProductService(BaseRepository<Product, Long> productRepository) {
        this.baseRepository = productRepository;
    }
}
