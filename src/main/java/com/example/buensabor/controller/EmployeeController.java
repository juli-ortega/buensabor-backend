package com.example.buensabor.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.EmployeeDTO;
import com.example.buensabor.entity.dto.UpdateDTOs.EmployeeUpdateDTO;
import com.example.buensabor.service.EmployeeService;
import com.example.buensabor.service.PermissionEmployee;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(path = "api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController extends BaseControllerImplementation<EmployeeDTO, EmployeeService> {

    @Autowired
    private PermissionEmployee permissionEmployee;
    
    private Authentication authentication;

    private final EmployeeService employeeService;
    
    @Override
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            if (!permissionEmployee.canAccessEmployee(authentication, id)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "No tenes permisos para acceder"));
            }
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')") // Permitir acceso solo al rol ADMIN
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener a los empleados: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @GetMapping("/bycompany")
    public ResponseEntity<?> getEmployees() {
        try {
            return ResponseEntity.ok(service.getEmployeesByCompany());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener a los empleados: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")
    @PutMapping("/bycompany/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        try {
            return ResponseEntity.ok(service.updateEmployeeDTO(id, employeeUpdateDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener a los empleados: " + e.getMessage());
        }
    }
    
}