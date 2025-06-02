package com.example.buensabor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Auth.Roles.Roles;
import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.User;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.service.CompanyService;

import lombok.NoArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/company")
@NoArgsConstructor
public class CompanyController extends BaseControllerImplementation<CompanyDTO, CompanyService> {
    
    private Authentication authentication;

    @Override
    public ResponseEntity<?> getById(@PathVariable Long id) {    
        try {
            User user = (User) authentication.getPrincipal();

            // Si es ADMIN, puede pedir cualquier cliente
            if (user.getRole().equals(Roles.ADMIN)) {
                return ResponseEntity.ok(service.findById(id));
            }

            // Si es CLIENT, solo puede pedir su propio recurso
            if (user.getRole().equals(Roles.CLIENT)) {
                if (!user.getId().equals(id)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You haven't permission to access that, you aren't this client");
                }
                return ResponseEntity.ok(service.findById(id));
            }

            // Si llega otro rol
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You haven't permission to access that");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not foundsdad");
        }
    }
}
