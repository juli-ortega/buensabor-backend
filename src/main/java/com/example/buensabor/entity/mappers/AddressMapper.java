package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.dto.AddressDTO;
import com.example.buensabor.entity.dto.AddressDTOs.AddressBasicDTO;

@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<Address, AddressDTO> {

    @Override
    @Mapping(target = "city", source = "city")
    AddressDTO toDTO(Address entity);

    @Override
    @Mapping(target = "city", source = "city")
    Address toEntity(AddressDTO dto);

    @Mapping(source = "city.id", target = "cityId")
    AddressBasicDTO toBasicDTO(Address address);
}