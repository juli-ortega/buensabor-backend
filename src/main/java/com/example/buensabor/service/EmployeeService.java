package com.example.buensabor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.entity.dto.EmployeeDTO;
import com.example.buensabor.entity.mappers.EmployeeMapper;
import com.example.buensabor.repository.AddressRepository;
import com.example.buensabor.repository.CityRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.EmployeeRepository;
import com.example.buensabor.service.interfaces.IEmployeeService;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService extends BaseServiceImplementation<EmployeeDTO, Employee, Long> implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        super(employeeRepository, employeeMapper);
    }

    @Override
    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO) throws Exception{

        // Obtener la city
        City city = cityRepository.findById(employeeDTO.getAddress().getCity().getId())
        .orElseThrow(() -> new RuntimeException("City not found"));
        
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Company company = companyRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        // Crear address
        Address address = new Address();
        address.setStreet(employeeDTO.getAddress().getStreet());
        address.setNumber(employeeDTO.getAddress().getNumber());
        address.setPostalCode(employeeDTO.getAddress().getPostalCode());
        address.setCity(city);
        addressRepository.save(address);

        // Crear employee
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee.setPassword(encoder.encode(employeeDTO.getPassword()));
        employee.setCompany(company);
        employee.setAddress(address);
        employeeRepository.save(employee);

        return employeeMapper.toDTO(employee);

    }
}
