package com.example.buensabor.service;

import org.springframework.stereotype.Service;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.ClientAddress;
import com.example.buensabor.entity.dto.ClientAddressDTO;
import com.example.buensabor.entity.dto.ClientAddressWithAddressDTO;
import com.example.buensabor.entity.mappers.ClientAddressMapper;
import com.example.buensabor.repository.ClientAddressRepository;
import com.example.buensabor.repository.ClientRepository;
import com.example.buensabor.repository.AddressRepository;
import com.example.buensabor.service.interfaces.IClientAddressService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientAddressService extends BaseServiceImplementation<ClientAddressDTO, ClientAddress, Long> implements IClientAddressService {

    private final ClientAddressRepository clientAddressRepository;
    private final ClientAddressMapper clientAddressMapper;
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    public ClientAddressService(ClientAddressRepository clientAddressRepository, ClientAddressMapper clientAddressMapper, ClientRepository clientRepository, AddressRepository addressRepository) {
        super(clientAddressRepository, clientAddressMapper);
        this.clientAddressRepository = clientAddressRepository;
        this.clientAddressMapper = clientAddressMapper;
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
    }

    public List<ClientAddressWithAddressDTO> findByClientId(Long clientId) {
        return clientAddressRepository.findAll().stream()
            .filter(ca -> ca.getIsActive() && ca.getClient().getId().equals(clientId))
            .map(clientAddressMapper::toWithAddressDTO)
            .collect(Collectors.toList());
    }

    public ClientAddressWithAddressDTO saveWithAddress(ClientAddressDTO dto) {
        ClientAddress entity = clientAddressMapper.toEntity(dto);
        entity.setClient(clientRepository.findById(dto.getClientId()).orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
        entity.setAddress(addressRepository.findById(dto.getAddressId()).orElseThrow(() -> new RuntimeException("Dirección no encontrada")));
        entity.setIsActive(true);
        clientAddressRepository.save(entity);
        return clientAddressMapper.toWithAddressDTO(entity);
    }

    public ClientAddressWithAddressDTO updateWithAddress(Long id, ClientAddressDTO dto) {
        ClientAddress entity = clientAddressRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe la relación"));
        if (!entity.getClient().getId().equals(dto.getClientId())) {
            throw new RuntimeException("No autorizado para modificar esta relación");
        }
        entity.setAddress(addressRepository.findById(dto.getAddressId()).orElseThrow(() -> new RuntimeException("Dirección no encontrada")));
        clientAddressRepository.save(entity);
        return clientAddressMapper.toWithAddressDTO(entity);
    }

    public void logicalDelete(Long id, Long clientId) {
        ClientAddress entity = clientAddressRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe la relación"));
        if (!entity.getClient().getId().equals(clientId)) {
            throw new RuntimeException("No autorizado para eliminar esta relación");
        }
        entity.setIsActive(false);
        clientAddressRepository.save(entity);
    }
}
