package com.example.buensabor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
import com.example.buensabor.entity.dto.ResponseDTOs.EmployeeResponseDTO;
import com.example.buensabor.entity.dto.UpdateDTOs.EmployeeUpdateDTO;
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

    private Company getAuthenticatedCompany() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return companyRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Compania no encontrada"));
    }

    public List<EmployeeResponseDTO> getEmployeesByCompany() {
        Company company = getAuthenticatedCompany();

        List<Employee> employees = employeeRepository.findByCompanyId(company.getId());
        employees = employees.stream().filter(e -> e.getIsActive() == null || e.getIsActive()).collect(Collectors.toList());

        return employees.stream()
            .map(employeeMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeResponseDTO createEmployeeDTO(EmployeeCreateDTO employeeCreateDT0) throws Exception{

        // Obtener la city
        City city = cityRepository.findById(employeeCreateDT0.getAddressBasicDTO().getCityId())
            .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));
        
        Company company = getAuthenticatedCompany();

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

        return employeeMapper.toResponseDTO(employee);

    }

    @Transactional
    public EmployeeResponseDTO updateEmployeeDTO(Long employeeId, EmployeeUpdateDTO employeeUpdateDTO) throws Exception {
        // Buscar employee
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Company company = getAuthenticatedCompany();

        // Verificar si el empleado pertenece a la misma compañía
        if (!employee.getCompany().getId().equals(company.getId())) {
            throw new AccessDeniedException("No tiene permiso para modificar este empleado");
        }

        // Obtener city
        City city = cityRepository.findById(employeeUpdateDTO.getAddressBasicDTO().getCityId())
            .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        // Actualizar dirección
        Address address = employee.getAddress();
        address.setStreet(employeeUpdateDTO.getAddressBasicDTO().getStreet());
        address.setNumber(employeeUpdateDTO.getAddressBasicDTO().getNumber());
        address.setPostalCode(employeeUpdateDTO.getAddressBasicDTO().getPostalCode());
        address.setCity(city);
        addressRepository.save(address);

        // Actualizar datos del employee usando mapper
        employeeMapper.updateEmployeeFromDTO(employeeUpdateDTO, employee);

        // Si viene password, encriptar y setear
        if (employeeUpdateDTO.getPassword() != null && !employeeUpdateDTO.getPassword().isEmpty()) {
            employee.setPassword(encoder.encode(employeeUpdateDTO.getPassword()));
        }

        employeeRepository.save(employee);

        return employeeMapper.toResponseDTO(employee);
    }

}
