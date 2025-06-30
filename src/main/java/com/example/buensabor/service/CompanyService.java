package com.example.buensabor.service;

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
    
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public CompanyService(
        CompanyRepository companyRepository,
        CompanyMapper companyMapper,
        CityRepository cityRepository,
        AddressRepository addressRepository,
        UserRepository userRepository
    ) {
        super(companyRepository, companyMapper);
        this.companyMapper = companyMapper;
        this.companyRepository = companyRepository;
        this.cityRepository = cityRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO) throws Exception{

        if (userRepository.findByEmail(companyDTO.getEmail()).isPresent()) {
            throw new RuntimeException("La compania ya esta registrada");
        }

        // Obtener la city
        City city = cityRepository.findById(companyDTO.getAddress().getCity().getId())
            .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

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

    @Override
    @Transactional
    public java.util.List<CompanyDTO> findAll() throws Exception {
        java.util.List<CompanyDTO> all = super.findAll();
        all.removeIf(c -> c.getIsActive() != null && !c.getIsActive());
        return all;
    }
}
