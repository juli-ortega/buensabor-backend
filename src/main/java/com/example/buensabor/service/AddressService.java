package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.dto.AddressDTO;
import com.example.buensabor.entity.mappers.AddressMapper;
import com.example.buensabor.repository.AddressRepository;
import com.example.buensabor.repository.CityRepository;
import com.example.buensabor.service.interfaces.IAddressService;

@Service
public class AddressService extends BaseServiceImplementation<AddressDTO, Address, Long> implements IAddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CityRepository cityRepository;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper, CityRepository cityRepository) {
        super(addressRepository, addressMapper);
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.cityRepository = cityRepository;
    }

    @Override
    public AddressDTO save(AddressDTO addressDTO) throws Exception {
        // Obtener la entidad City completa usando el id del DTO
        Long cityId = addressDTO.getCity() != null ? addressDTO.getCity().getId() : null;
        if (cityId == null) {
            throw new RuntimeException("City id is required");
        }
        City city = cityRepository.findById(cityId)
            .orElseThrow(() -> new RuntimeException("City not found with id: " + cityId));
        Address address = addressMapper.toEntity(addressDTO);
        address.setCity(city);
        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }
}
