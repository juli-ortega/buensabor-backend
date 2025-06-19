package com.example.buensabor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.buensabor.entity.ProductPromotion;
import com.example.buensabor.entity.dto.ProductPromotionDTO;
import com.example.buensabor.entity.mappers.ProductPromotionMapper;
import com.example.buensabor.repository.ProductPromotionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductPromotionService {

    private final ProductPromotionRepository productPromotionRepository;
    private final ProductPromotionMapper productPromotionMapper;

    public List<ProductPromotionDTO> getAll() {
        List<ProductPromotion> list = productPromotionRepository.findAll();
        return list.stream().map(productPromotionMapper::toDTO).collect(Collectors.toList());
    }

    public ProductPromotionDTO createProductPromotion(ProductPromotionDTO dto) {
        ProductPromotion entity = productPromotionMapper.toEntity(dto);
        ProductPromotion saved = productPromotionRepository.save(entity);
        return productPromotionMapper.toDTO(saved);
    }

    public ProductPromotionDTO update(Long id, ProductPromotionDTO dto) {
        ProductPromotion entity = productPromotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductPromotion not found"));
        // update fields if needed (depends on your DTO)
        ProductPromotion updated = productPromotionRepository.save(entity);
        return productPromotionMapper.toDTO(updated);
    }

    public boolean delete(Long id) {
        ProductPromotion entity = productPromotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductPromotion not found"));
        productPromotionRepository.delete(entity);
        return true;
    }
}

