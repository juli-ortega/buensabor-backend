package com.example.buensabor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.service.IngredientService;

@RestController
@RequestMapping(path = "api/v1/ingredients")
public class IngredientController extends BaseControllerImplementation<IngredientDTO, IngredientService> {

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @GetMapping("/nottoprepare")
    public ResponseEntity<?> getNotToPrepareByCompany() {
        try {
            return ResponseEntity.ok(service.getNotToPrepareByCompany());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @GetMapping("/toprepare")
    public ResponseEntity<?> getToPrepareByCompany() {
        try {
            return ResponseEntity.ok(service.getToPrepareByCompany());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @Override
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(service.getAllByCompany());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

}