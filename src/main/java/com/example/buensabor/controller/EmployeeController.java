package com.example.buensabor.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.entity.dto.EmployeeDTO;
import com.example.buensabor.service.EmployeeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/employee")
public class EmployeeController extends BaseControllerImplementation<EmployeeDTO, EmployeeService> {
}