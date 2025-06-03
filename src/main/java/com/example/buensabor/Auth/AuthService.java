package com.example.buensabor.Auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.JWT.JWTService;
import com.example.buensabor.Auth.Roles.Roles;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.entity.User;
import com.example.buensabor.entity.dto.ClientDTO;
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

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Registrar Client
    @Transactional
    public Map<String, Object> registerClient(ClientDTO clientDTO) {

        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(clientDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Client already registered");
        }

        // Crear y guardar el cliente
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setLastname(clientDTO.getLastname());
        client.setEmail(clientDTO.getEmail());
        client.setPhone(clientDTO.getPhone());
        client.setBorn_date(clientDTO.getBorn_date());
        client.setGenero(clientDTO.getGenero());
        client.setPassword(encoder.encode(clientDTO.getPassword()));
        client.setRole(Roles.CLIENT);
        userRepository.save(client);

        // Generar token JWT
        String token = jwtService.generateToken(client);

        // Armar respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("client", client);

        return response;
    }

    // Login común para todos
    public String login(String email, String password) {
        try {
            // Autenticar las credenciales
            Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
            // Validar autenticación y existencia de usuario
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(getUser(email)); 
            } else {
                throw new RuntimeException("User not found");
            }

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("User or password incorrect");
        }
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

}

