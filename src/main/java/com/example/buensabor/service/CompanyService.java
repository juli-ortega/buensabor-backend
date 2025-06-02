package com.example.buensabor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.Roles.Roles;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.entity.mappers.CompanyMapper;
import com.example.buensabor.repository.AddressRepository;
import com.example.buensabor.repository.CityRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.UserRepository;
import com.example.buensabor.service.interfaces.ICompanyService;

import jakarta.transaction.Transactional;

@Service
public class CompanyService extends BaseServiceImplementation< CompanyDTO, Company, Long> implements ICompanyService {
    
    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO) throws Exception{

        if (userRepository.findByEmail(companyDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Company already registered");
        }

        // Obtener la city
        City city = cityRepository.findById(companyDTO.getAddress().getCity().getId())
            .orElseThrow(() -> new RuntimeException("City not found"));

        // Crear address
        Address address = new Address();
        address.setStreet(companyDTO.getAddress().getStreet());
        address.setNumber(companyDTO.getAddress().getNumber());
        address.setPostalCode(companyDTO.getAddress().getPostalCode());
        address.setCity(city);
        addressRepository.save(address);

        // Crear company
        Company company = companyMapper.toEntity(companyDTO);
        company.setPassword(encoder.encode(companyDTO.getPassword()));
        company.setRole(Roles.COMPANY);
        company.setAddress(address);
        companyRepository.save(company);

        return companyMapper.toDTO(company);

    }
}
