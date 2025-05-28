package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.CompanyDTO;

@Mapper(componentModel = "spring")
public interface CompanyMapper extends BaseMapper<Company, CompanyDTO> {

    @Override
    CompanyDTO toDTO(Company entity);

    @Override
    Company toEntity(CompanyDTO dto);
}
