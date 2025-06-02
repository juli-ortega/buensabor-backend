package com.example.buensabor.Auth;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.buensabor.entity.dto.ClientDTO;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.entity.dto.EmployeeDTO;
import com.example.buensabor.service.CompanyService;
import com.example.buensabor.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    // Registro Client
    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(userService.registerClient(clientDTO));
    }

    // Registro Company
    @PostMapping("/register/company")
    public ResponseEntity<?> registerCompany(@RequestBody CompanyDTO companyDTO) {
        try {
            CompanyDTO newCompanyDTO = companyService.save(companyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCompanyDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not can't create the company" + e.getMessage());
        }
    }

    // Registro Employee
    @PostMapping("/register/employee")
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY')")    
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO newEmployeeDTO = employeeService.save(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newEmployeeDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not can't create the employee");
        }
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
