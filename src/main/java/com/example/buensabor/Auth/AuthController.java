package com.example.buensabor.Auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.entity.dto.ClientDTO;
import com.example.buensabor.entity.dto.CompanyDTO;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;

    // Registro Client
    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(userService.registerClient(clientDTO));
    }

    // Registro Company
    @PostMapping("/register/company")
    public ResponseEntity<?> registerCompany(@RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(userService.registerCompany(companyDTO));
    }

    // Registro Employee (requiere autenticación previa de Company)
    @PostMapping("/register/employee")
    @PreAuthorize("hasAuthority('COMPANY')")  // solo Companies pueden usar este endpoint
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
        Employee newEmployee = userService.registerEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    // Login para cualquier usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("Message", "You are login succesfully");
        return ResponseEntity.ok(response);
    }
}
