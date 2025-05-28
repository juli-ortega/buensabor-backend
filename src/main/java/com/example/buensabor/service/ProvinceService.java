package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Province;
import com.example.buensabor.entity.dto.ProvinceDTO;
import com.example.buensabor.entity.mappers.ProvinceMapper;
import com.example.buensabor.repository.ProvinceRepository;
import com.example.buensabor.service.interfaces.IProvinceService;

@Service
public class ProvinceService extends BaseServiceImplementation<ProvinceDTO, Province, Long> implements IProvinceService {

    public ProvinceService(ProvinceRepository provinceRepository, ProvinceMapper provinceMapper) {
        super(provinceRepository, provinceMapper);
    }
}
