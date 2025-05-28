package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.dto.AddressDTO;
import com.example.buensabor.entity.mappers.AddressMapper;
import com.example.buensabor.repository.AddressRepository;
import com.example.buensabor.service.interfaces.IAddressService;

@Service
public class AddressService extends BaseServiceImplementation<AddressDTO, Address, Long> implements IAddressService {

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        super(addressRepository, addressMapper);
    }
}
