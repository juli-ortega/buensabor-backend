package com.example.buensabor.entity.mappers;

import org.mapstruct.Mapper;

import com.example.buensabor.Bases.BaseMapper;
import com.example.buensabor.entity.Facture;
import com.example.buensabor.entity.dto.FactureDTO;

@Mapper(componentModel = "spring")
public interface FactureMapper extends BaseMapper<Facture, FactureDTO> {
    @Override
    FactureDTO toDTO(Facture entity);

    @Override
    Facture toEntity(FactureDTO dto);
}