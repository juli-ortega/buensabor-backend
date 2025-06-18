package com.example.buensabor.entity.dto.EmployeeDTOs;

import java.math.BigInteger;
import java.sql.Date;

import com.example.buensabor.entity.dto.UserDTO;
import com.example.buensabor.entity.dto.AddressDTOs.AddressBasicDTO;
import com.example.buensabor.entity.enums.GeneroEnum;
import com.example.buensabor.entity.enums.RoleEmployee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO extends UserDTO {
    private String name;
    private String email;
    private String lastname;
    private BigInteger phone;
    private Date born_date;
    private GeneroEnum genero;
    private RoleEmployee roleEmployee;
    private AddressBasicDTO addressBasicDTO;
}
