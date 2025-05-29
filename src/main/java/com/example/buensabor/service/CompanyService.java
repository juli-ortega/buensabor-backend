package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.CityDTO;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.entity.mappers.CompanyMapper;
import com.example.buensabor.repository.AddressRepository;
import com.example.buensabor.repository.CityRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.service.interfaces.ICompanyService;

import jakarta.transaction.Transactional;

@Service
public class CompanyService extends BaseServiceImplementation< CompanyDTO, Company, Long> implements ICompanyService {

    private CompanyMapper companyMapper;
    private CityRepository cityRepository;
    private AddressRepository addressRepository;
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, CityRepository cityRepository, AddressRepository addressRepository) {
        super(companyRepository, companyMapper);
        
        this.companyMapper = companyMapper;
        this.companyRepository = companyRepository;
        this.cityRepository = cityRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO) throws Exception{

        System.out.println(companyDTO.getAddress().getCity());

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
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setEmail(companyDTO.getEmail());
        company.setPassword(companyDTO.getPassword());
        company.setPhone(companyDTO.getPhone());
        company.setCuit(companyDTO.getCuit());
        company.setAddress(address);
        companyRepository.save(company);

        return companyMapper.toDTO(company);

    }
}
