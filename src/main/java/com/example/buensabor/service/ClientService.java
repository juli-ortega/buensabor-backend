package com.example.buensabor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.Roles.Roles;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.Util.SecurityUtil;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.ClientDTO;
import com.example.buensabor.entity.dto.ClientInfoDTO;
import com.example.buensabor.entity.mappers.ClientMapper;
import com.example.buensabor.repository.ClientRepository;
import com.example.buensabor.repository.UserRepository;
import com.example.buensabor.service.interfaces.IClientService;

import jakarta.transaction.Transactional;

@Service
public class ClientService extends BaseServiceImplementation< ClientDTO, Client, Long> implements IClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, UserRepository userRepository, SecurityUtil securityUtil) {
        super(clientRepository, clientMapper);
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.userRepository = userRepository;
        this.securityUtil = securityUtil;
    }

    @Override
    @Transactional
    public ClientDTO save(ClientDTO clientDTO) {

        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(clientDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Cliente ya esta registrada");
        }

        // Crear y guardar el cliente
        Client client = clientMapper.toEntity(clientDTO);
        client.setPassword(encoder.encode(clientDTO.getPassword()));
        client.setRole(Roles.CLIENT);
        clientRepository.save(client);

        return clientMapper.toDTO(client);

    }

    @Override
    @Transactional
    public java.util.List<ClientDTO> findAll() throws Exception {
        java.util.List<ClientDTO> all = super.findAll();
        all.removeIf(c -> c.getIsActive() != null && !c.getIsActive());
        return all;
    }

    @Transactional
    public List<ClientDTO> findAllByCompany() throws Exception {
        Company company = securityUtil.getAuthenticatedCompany();
        List<Client> clients = clientRepository.findClientsWithOrdersByCompany(company);
        return clients.stream().map(clientMapper::toDTO).collect(Collectors.toList());
    }

    public ClientInfoDTO getAuthenticatedClientInfo() {
        Client client = securityUtil.getAuthenticatedClient();
        return clientMapper.toInfoDTO(client);
    }
}
