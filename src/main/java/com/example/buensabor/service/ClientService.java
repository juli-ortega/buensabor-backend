package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.dto.ClientDTO;
import com.example.buensabor.entity.mappers.ClientMapper;
import com.example.buensabor.repository.ClientRepository;
import com.example.buensabor.service.interfaces.IClientService;

@Service
public class ClientService extends BaseServiceImplementation< ClientDTO, Client, Long> implements IClientService {

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        super(clientRepository, clientMapper);
    }
}
