package com.example.buensabor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.Roles.Roles;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.dto.ClientDTO;
import com.example.buensabor.entity.mappers.ClientMapper;
import com.example.buensabor.repository.ClientRepository;
import com.example.buensabor.repository.UserRepository;
import com.example.buensabor.service.interfaces.IClientService;

import jakarta.transaction.Transactional;

@Service
public class ClientService extends BaseServiceImplementation< ClientDTO, Client, Long> implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private UserRepository userRepository;
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    @Transactional
    public ClientDTO save(ClientDTO clientDTO) {

        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(clientDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Client already registered");
        }

        // Crear y guardar el cliente
        Client client = clientMapper.toEntity(clientDTO);
        client.setPassword(encoder.encode(clientDTO.getPassword()));
        client.setRole(Roles.CLIENT);
        clientRepository.save(client);

        return clientMapper.toDTO(client);

    }
}
