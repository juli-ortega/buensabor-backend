package com.example.buensabor.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.repository.ClientRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ClientRepository clientRepository;

    public Company getAuthenticatedCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        boolean isCompany = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_COMPANY"));

        boolean isEmployee = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE"));

        if (isCompany) {
            return companyRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("Compañía no encontrada"));
        } else if (isEmployee) {
            Employee employee = employeeRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            if (employee.getCompany() == null) {
                throw new RuntimeException("El empleado no está asignado a ninguna compañía.");
            }

            return employee.getCompany();
        } else {
            throw new RuntimeException("No autorizado: el usuario no tiene permisos para acceder a esta información.");
        }
    }

    public Client getAuthenticatedClient() {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Long clientId = ((com.example.buensabor.Auth.CustomUserDetails) authentication.getPrincipal()).getId();
        return clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
    }
}