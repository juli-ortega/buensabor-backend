package com.example.buensabor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.buensabor.Auth.CustomUserDetails;
import com.example.buensabor.Auth.Roles.Roles;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Address;
import com.example.buensabor.entity.City;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.entity.dto.EmployeeDTO;
import com.example.buensabor.entity.dto.CreateDTOs.EmployeeCreateDTO;
import com.example.buensabor.entity.dto.EmployeeDTOs.EmployeeResponseDTO;
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

    public List<EmployeeResponseDTO> getEmployeesByCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Company company = companyRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        List<Employee> employees = employeeRepository.findByCompanyId(company.getId());

        return employees.stream()
            .map(employeeMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeDTO createEmployeeDTO(EmployeeCreateDTO employeeCreateDT0) throws Exception{

        // Obtener la city
        City city = cityRepository.findById(employeeCreateDT0.getAddressBasicDTO().getCityId())
            .orElseThrow(() -> new RuntimeException("City not found"));
        
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Company company = companyRepository.findById(userDetails.getId())
            .orElseThrow(() -> new RuntimeException("Company not found"));

        // Crear address
        Address address = new Address();
        address.setStreet(employeeCreateDT0.getAddressBasicDTO().getStreet());
        address.setNumber(employeeCreateDT0.getAddressBasicDTO().getNumber());
        address.setPostalCode(employeeCreateDT0.getAddressBasicDTO().getPostalCode());
        address.setCity(city);
        addressRepository.save(address);

        // Crear employee
        Employee employee = employeeMapper.toEntity(employeeCreateDT0);
        employee.setPassword(encoder.encode(employeeCreateDT0.getPassword()));
        employee.setRole(Roles.EMPLOYEE);
        employee.setCompany(company);
        employee.setAddress(address);
        employeeRepository.save(employee);

        return employeeMapper.toDTO(employee);

    }
}
