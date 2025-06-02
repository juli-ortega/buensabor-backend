package com.example.buensabor.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissionEmployee {

    private final EmployeeRepository employeeRepository;

    // Método que verifica si el usuario autenticado puede acceder/modificar/eliminar el recurso con id
    public boolean canAccessEmployee(Authentication authentication, Long resourceId) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return user.getId().equals(resourceId);
        }

        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY"))) {
            return employeeRepository.existsByIdAndCompanyId(resourceId, user.getId());
        }

        return false;
    }

}
