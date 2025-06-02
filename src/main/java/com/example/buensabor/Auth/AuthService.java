package com.example.buensabor.Auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.JWT.JWTService;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.repository.AddressRepository;
import com.example.buensabor.repository.CityRepository;
import com.example.buensabor.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AddressRepository addressRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Registrar Client
    public String registerClient(Client client) {
        if (userRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new RuntimeException("Client already registered");
        }

        client.setPassword(encoder.encode(client.getPassword()));
        userRepository.save(client);

        return jwtService.generateToken(client.getEmail());
    }

    @Transactional
    public Map<String, Object> registerCompany(CompanyDTO companyDTO) {

        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(companyDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Company already registered");
        }

        // Buscar la ciudad por ID
        City city = cityRepository.findById(companyDTO.getAddress().getCity().getId())
                .orElseThrow(() -> new RuntimeException("City not found"));

        // Crear y guardar la dirección
        Address address = new Address();
        address.setStreet(companyDTO.getAddress().getStreet());
        address.setNumber(companyDTO.getAddress().getNumber());
        address.setPostalCode(companyDTO.getAddress().getPostalCode());
        address.setCity(city);
        addressRepository.save(address);

        // Crear y guardar la empresa
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setEmail(companyDTO.getEmail());
        company.setPhone(companyDTO.getPhone());
        company.setCuit(companyDTO.getCuit());
        company.setAddress(address);
        company.setPassword(encoder.encode(companyDTO.getPassword()));

        userRepository.save(company);

        // Generar token JWT
        String token = jwtService.generateToken(company.getEmail());

        // Armar respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("company", company);

        return response;
    }

    // Registrar Employee (la company debe pasarla ya asociada)
    public Employee registerEmployee(Employee employee) {
        if (userRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new RuntimeException("Employee already registered");
        }

        employee.setPassword(encoder.encode(employee.getPassword()));
        return userRepository.save(employee);
    }

    // Login común para todos
    public String login(String email, String password) {
        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(email);
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("User or password incorrect");
        }
    }
}

