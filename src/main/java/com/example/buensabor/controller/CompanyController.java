package com.example.buensabor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.service.CompanyService;

import lombok.NoArgsConstructor;

@RestController
// @CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/company")
@NoArgsConstructor
public class CompanyController extends BaseControllerImplementation<CompanyDTO, CompanyService> {
    
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY') and (hasRole('ADMIN') or #id == authentication.principal.id)")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }
}
