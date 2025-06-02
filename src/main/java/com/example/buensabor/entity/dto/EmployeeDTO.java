package com.example.buensabor.entity.dto;

import java.sql.Date;

import com.example.buensabor.entity.enums.GeneroEnum;
import com.example.buensabor.entity.enums.RoleEmployee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO extends UserDTO {
    private String name;
    private String email;
    private String password;
    private String lastname;
    private Date born_date;
    private GeneroEnum genero;
    private RoleEmployee roleEmployee;
    private CompanyDTO company;
    private AddressDTO address;
}
