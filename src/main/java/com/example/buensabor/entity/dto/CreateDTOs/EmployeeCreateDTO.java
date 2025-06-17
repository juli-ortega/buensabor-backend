package com.example.buensabor.entity.dto.CreateDTOs;

import java.math.BigInteger;
import java.sql.Date;

import com.example.buensabor.entity.dto.AddressDTOs.AddressBasicDTO;
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
public class EmployeeCreateDTO {
    private String name;
    private String email;
    private String password;
    private BigInteger phone;
    private String lastname;
    private Date born_date;
    private GeneroEnum genero;
    private RoleEmployee roleEmployee;
    private AddressBasicDTO addressBasicDTO;
}
