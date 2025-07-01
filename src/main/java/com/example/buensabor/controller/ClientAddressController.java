package com.example.buensabor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.entity.dto.ClientAddressDTO;
import com.example.buensabor.entity.dto.ClientAddressWithAddressDTO;
import com.example.buensabor.service.ClientAddressService;

@RestController
@RequestMapping(path = "api/v1/client-addresses")
public class ClientAddressController {

    private final ClientAddressService clientAddressService;

    public ClientAddressController(ClientAddressService clientAddressService) {
        this.clientAddressService = clientAddressService;
    }

    // Cliente obtiene sus direcciones
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ClientAddressWithAddressDTO>> getMyAddresses() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ClientAddressWithAddressDTO> addresses = clientAddressService.findByClientId(userDetails.getId());
        return ResponseEntity.ok(addresses);
    }

    // Admin/Compañía obtiene direcciones de un cliente
    @GetMapping("/{clientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    public ResponseEntity<List<ClientAddressWithAddressDTO>> getAddressesByClientId(@PathVariable Long clientId) {
        List<ClientAddressWithAddressDTO> addresses = clientAddressService.findByClientId(clientId);
        return ResponseEntity.ok(addresses);
    }

    // Crear nueva relación cliente-dirección
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientAddressWithAddressDTO> create(@RequestBody ClientAddressDTO dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setClientId(userDetails.getId());
        ClientAddressWithAddressDTO created = clientAddressService.saveWithAddress(dto);
        return ResponseEntity.ok(created);
    }

    // Actualizar relación cliente-dirección
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientAddressWithAddressDTO> update(@PathVariable Long id, @RequestBody ClientAddressDTO dto) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setClientId(userDetails.getId());
        ClientAddressWithAddressDTO updated = clientAddressService.updateWithAddress(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Borrado lógico
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        clientAddressService.logicalDelete(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}