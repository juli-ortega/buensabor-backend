package com.example.buensabor.Auth;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.buensabor.entity.User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Definir los roles según tipo de usuario
        String role;
        if (user instanceof Company) {
            role = "COMPANY";
        } else if (user instanceof Employee) {
            role = "EMPLOYEE";
        } else if (user instanceof Client) {
            role = "CLIENT";
        } else {
            role = "USER"; // fallback
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}
