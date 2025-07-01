package com.example.buensabor.entity.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.dto.ClientDTO;
import com.example.buensabor.entity.dto.ClientInfoDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper extends BaseMapper<Client, ClientDTO> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "role", target = "role")
    ClientDTO toDTO(Client entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "role", target = "role")
    Client toEntity(ClientDTO dto);

    // Nuevo método para ClientInfoDTO
    ClientInfoDTO toInfoDTO(Client entity);
}

