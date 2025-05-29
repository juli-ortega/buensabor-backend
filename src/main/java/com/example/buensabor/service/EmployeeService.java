package com.example.buensabor.service;

import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.entity.dto.EmployeeDTO;
import com.example.buensabor.entity.mappers.EmployeeMapper;
import com.example.buensabor.repository.EmployeeRepository;
import com.example.buensabor.service.interfaces.IEmployeeService;

@Service
public class EmployeeService extends BaseServiceImplementation<EmployeeDTO, Employee, Long> implements IEmployeeService {

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        super(employeeRepository, employeeMapper);
    }
}
