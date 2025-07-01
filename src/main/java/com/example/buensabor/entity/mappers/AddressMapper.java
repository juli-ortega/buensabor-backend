package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.dto.AddressDTO;
import com.example.buensabor.entity.dto.AddressDTOs.AddressBasicDTO;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.dto.CityDTOs.CityBasicDTO;

@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<Address, AddressDTO> {

    @Override
    AddressDTO toDTO(Address entity);

    @Override
    Address toEntity(AddressDTO dto);

    // Map Address to AddressBasicDTO, mapping city to CityBasicDTO
    @Mapping(target = "city", source = "city")
    AddressBasicDTO toBasicDTO(Address address);

    // Map City to CityBasicDTO
    CityBasicDTO cityToCityBasicDTO(City city);

    // Map CityBasicDTO to City (for completeness, if needed)
    City cityBasicDTOToCity(CityBasicDTO cityBasicDTO);
}