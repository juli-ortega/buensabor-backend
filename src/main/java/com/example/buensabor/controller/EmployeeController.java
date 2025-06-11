package com.example.buensabor.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.EmployeeDTO;
import com.example.buensabor.service.EmployeeService;
import com.example.buensabor.service.PermissionEmployee;

import lombok.NoArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/employee")
@NoArgsConstructor
public class EmployeeController extends BaseControllerImplementation<EmployeeDTO, EmployeeService> {

    @Autowired
    private PermissionEmployee permissionEmployee;
    
    private Authentication authentication;
    
    @Override
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            if (!permissionEmployee.canAccessEmployee(authentication, id)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "You don't have permission to access this resource"));
            }
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Message: "+ e.getMessage());
        }
    }

}