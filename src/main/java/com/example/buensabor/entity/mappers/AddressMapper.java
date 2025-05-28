package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.dto.AddressDTO;

@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<Address, AddressDTO> {

    @Override
    AddressDTO toDTO(Address entity);

    @Override
    Address toEntity(AddressDTO dto);
}
