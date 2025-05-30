package com.example.buensabor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.ProductDTO;
import com.example.buensabor.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/products")
@AllArgsConstructor
public class ProductController extends BaseControllerImplementation<ProductDTO, ProductService>  {

    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;  

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> create(@RequestPart("product") String productString,
                                    @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            ProductDTO productDTO = objectMapper.readValue(productString, ProductDTO.class);

            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = productService.saveImage(imageFile);
                productDTO.setImage(imageUrl);
            }

            return super.save(productDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


}
