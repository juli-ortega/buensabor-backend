package com.example.buensabor.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.ClientDTO;
import com.example.buensabor.service.ClientService;

import lombok.NoArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/client")
@NoArgsConstructor
public class ClientController extends BaseControllerImplementation<ClientDTO, ClientService> {

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENT') and #id == authentication.principal.id)")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

}