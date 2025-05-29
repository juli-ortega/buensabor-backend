package com.example.buensabor.entity.dto;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.enums.RoleEmployee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO extends BaseDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private RoleEmployee roleEmployee;
    private CompanyDTO company;
    private AddressDTO address;
}
