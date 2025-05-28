package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.dto.CityDTO;

@Mapper(componentModel = "spring")
public interface CityMapper extends BaseMapper<City, CityDTO> {

    @Override
    CityDTO toDTO(City entity);

    @Override
    City toEntity(CityDTO dto);
}
