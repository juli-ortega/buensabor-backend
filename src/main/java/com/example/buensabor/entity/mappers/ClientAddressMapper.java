package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.ClientAddress;
import com.example.buensabor.entity.dto.ClientAddressDTO;
import com.example.buensabor.entity.dto.ClientAddressWithAddressDTO;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface ClientAddressMapper extends BaseMapper<ClientAddress, ClientAddressDTO> {

    @Override
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "address.id", target = "addressId")
    ClientAddressDTO toDTO(ClientAddress entity);

    @Override
    @Mapping(target = "client.id", source = "clientId")
    @Mapping(target = "address.id", source = "addressId")
    ClientAddress toEntity(ClientAddressDTO dto);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "address", target = "address")
    ClientAddressWithAddressDTO toWithAddressDTO(ClientAddress entity);
}
