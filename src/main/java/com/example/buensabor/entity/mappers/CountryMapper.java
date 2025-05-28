package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Country;
import com.example.buensabor.entity.dto.CountryDTO;

@Mapper(componentModel = "spring")
public interface CountryMapper extends BaseMapper<Country, CountryDTO> {

    @Override
    CountryDTO toDTO(Country entity);

    @Override
    Country toEntity(CountryDTO dto);
}
