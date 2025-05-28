package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Province;
import com.example.buensabor.entity.dto.ProvinceDTO;

@Mapper(componentModel = "spring")
public interface ProvinceMapper extends BaseMapper<Province, ProvinceDTO> {

    @Override
    ProvinceDTO toDTO(Province entity);

    @Override
    Province toEntity(ProvinceDTO dto);
}
