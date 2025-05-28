package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.entity.mappers.CompanyMapper;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.service.interfaces.ICompanyService;

@Service
public class CompanyService extends BaseServiceImplementation< CompanyDTO, Company, Long> implements ICompanyService {

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        super(companyRepository, companyMapper);
    }
}
