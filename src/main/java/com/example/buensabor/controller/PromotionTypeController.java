package com.example.buensabor.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.entity.dto.PromotionTypeDTO;
import com.example.buensabor.service.PromotionTypeService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/promotion-types")
@RequiredArgsConstructor
public class PromotionTypeController {

    private final PromotionTypeService promotionTypeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(promotionTypeService.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener los tipos de promociones" + e.getMessage());
        }
    }

    @GetMapping("/bycompany")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY', 'EMPLOYEE')")
    public ResponseEntity<List<PromotionTypeDTO>> getByCompany() {
        return ResponseEntity.ok(promotionTypeService.getByCompanyId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY', 'EMPLOYEE')")
    public ResponseEntity<PromotionTypeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(promotionTypeService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY', 'EMPLOYEE')")
    public ResponseEntity<PromotionTypeDTO> create(@RequestBody PromotionTypeDTO dto) {
        PromotionTypeDTO created = promotionTypeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY', 'EMPLOYEE')")
    public ResponseEntity<PromotionTypeDTO> update(@PathVariable Long id, @RequestBody PromotionTypeDTO dto) {
        PromotionTypeDTO updated = promotionTypeService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY', 'EMPLOYEE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        promotionTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
