package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Employee;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Long> {
     boolean existsByIdAndCompanyId(Long employeeId, Long companyId);
}
