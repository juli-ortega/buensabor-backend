package com.example.buensabor.Seeders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.dto.CompanyDTO;
import com.example.buensabor.repository.AddressRepository;
import com.example.buensabor.repository.CompanyRepository;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanySeedData implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public CompanySeedData(CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        
        // Leer datos de compañías desde JSON
        InputStream companyStream = getClass().getResourceAsStream("/data/companies.json");
        if (companyStream == null) {
            throw new IllegalArgumentException("No se pudo encontrar el archivo companies.json");
        }
        List<CompanyDTO> companyDTOs = mapper.readValue(companyStream, new TypeReference<List<CompanyDTO>>() {});
        List<Company> companies = companyDTOs.stream().map(dto -> {
            Address address = new Address();
            address.setStreet(dto.getAddress().getStreet());
            address.setNumber(dto.getAddress().getNumber());
            address.setPostalCode(dto.getAddress().getPostalCode());
            address.setCity(null); // Asignar ciudad si es necesario
            addressRepository.save(address);

            Company company = new Company();
            company.setName(dto.getName());
            company.setEmail(dto.getEmail());
            company.setPassword(encoder.encode(dto.getPassword()));
            company.setPhone(dto.getPhone());
            company.setCuit(dto.getCuit());
            company.setAddress(address);
            return company;
        }).collect(Collectors.toList());

        // Filtrar duplicados antes de guardar
        companies.removeIf(company -> companyRepository.findAll().stream()
            .anyMatch(existing -> existing.getName().equals(company.getName())));

        companyRepository.saveAll(companies);
    }
}
