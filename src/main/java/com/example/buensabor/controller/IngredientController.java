package com.example.buensabor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.IngredientDTO;
import com.example.buensabor.service.IngredientService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/ingredients")
public class IngredientController extends BaseControllerImplementation<IngredientDTO, IngredientService> {
    
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @GetMapping("/nottoprepare")
    public ResponseEntity<?> getNotToPrepare() {
        try {
            return ResponseEntity.ok(service.getNotToPrepare());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @GetMapping("/toprepare")
    public ResponseEntity<?> getToPrepare() {
        try {
            return ResponseEntity.ok(service.getToPrepare());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

}