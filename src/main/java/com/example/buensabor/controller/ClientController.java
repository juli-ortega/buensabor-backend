package com.example.buensabor.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.ClientDTO;
import com.example.buensabor.service.ClientService;

import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/client")
@NoArgsConstructor
public class ClientController extends BaseControllerImplementation<ClientDTO, ClientService> {

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') and (hasRole('ADMIN') or #id == principal.id)")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')") // Permitir acceso a ADMIN y CLIENT
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            if (org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.ok(service.findAll());
            } else {
                return ResponseEntity.ok(service.getAuthenticatedClientInfo());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener los clientes: " + e.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY', 'EMPLOYEE')") // Permitir acceso solo al rol ADMIN
    @GetMapping("/bycompany")
    public ResponseEntity<?> getAllByCompany() {
        try {
            return ResponseEntity.ok(service.findAllByCompany());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener los clientes: " + e.getMessage());
        }
    }

}