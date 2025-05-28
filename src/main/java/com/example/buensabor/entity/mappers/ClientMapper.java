package com.example.buensabor.entity.mappers;
import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper extends BaseMapper<Client, ClientDTO> {

    @Override
    ClientDTO toDTO(Client entity);

    @Override
    Client toEntity(ClientDTO dto);
}

