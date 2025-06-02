package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.CompanyDTO;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface CompanyMapper extends BaseMapper<Company, CompanyDTO> {

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "cuit", target = "cuit")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "role", target = "role")
    CompanyDTO toDTO(Company entity);

    @Override
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "cuit", target = "cuit")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "role", target = "role")
    Company toEntity(CompanyDTO dto);
}

