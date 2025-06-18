package com.example.buensabor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.buensabor.entity.dto.ProductPromotionDTO;
import com.example.buensabor.service.ProductPromotionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/productpromotion")
@RequiredArgsConstructor
public class ProductPromotionController {

    private final ProductPromotionService productPromotionService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','COMPANY', 'EMPLOYEE')")
    public ResponseEntity<List<ProductPromotionDTO>> getAll() {
        return ResponseEntity.ok(productPromotionService.getAll());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
    public ResponseEntity<ProductPromotionDTO> create(@RequestBody ProductPromotionDTO dto) {
        return ResponseEntity.ok(productPromotionService.createProductPromotion(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
    public ResponseEntity<ProductPromotionDTO> update(@PathVariable Long id, @RequestBody ProductPromotionDTO dto) {
        return ResponseEntity.ok(productPromotionService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productPromotionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
