package com.example.buensabor.Products.Product;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.Products.Product.Interfaces.IProductController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/products")
public class ProductController extends BaseControllerImplementation<Product, ProductService> implements IProductController {
    // ...existing code...
}
