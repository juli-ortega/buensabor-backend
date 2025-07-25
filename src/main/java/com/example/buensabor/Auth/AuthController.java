package com.example.buensabor.Auth;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.entity.dto.ClientDTO;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.entity.dto.CreateDTOs.EmployeeCreateDTO;
import com.example.buensabor.entity.dto.ResponseDTOs.EmployeeResponseDTO;
import com.example.buensabor.service.ClientService;
import com.example.buensabor.service.CompanyService;
import com.example.buensabor.service.EmployeeService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/public/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;
    private final ClientService clientService;

    // Registro Client
    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(@RequestBody ClientDTO clientDTO) {
        try {
            ClientDTO newClientDTO = clientService.save(clientDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClientDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not can't create the client" + e.getMessage());
        }
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
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeCreateDTO employeeCreateDTO) {
        try {
            EmployeeResponseDTO newEmployeeDTO = employeeService.createEmployeeDTO(employeeCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newEmployeeDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not can't create the employee: " + e.getMessage());    
        }
    }

    // Login para cualquier usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            LoginResponse loginResponse = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
            Map<String, Object> body = new HashMap<>();
            body.put("message", "You are logged in successfully");
            body.put("User", loginResponse.getUser());

            Cookie cookie = new Cookie("token", loginResponse.getToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60);

            response.addCookie(cookie);

            return ResponseEntity.ok(body);

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                Cookie deleteCookie = new Cookie(cookie.getName(), null);
                deleteCookie.setHttpOnly(true);
                deleteCookie.setSecure(true); // o false si estás en local
                deleteCookie.setPath("/");
                deleteCookie.setMaxAge(0); // <- borra la cookie
                response.addCookie(deleteCookie);
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("message", "You have been logged out successfully");

        return ResponseEntity.ok(body);
    }

}
