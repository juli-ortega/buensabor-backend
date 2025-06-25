package com.example.buensabor.entity.dto.UpdateDTOs;

import java.math.BigInteger;
import java.util.Date;

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
public class EmployeeUpdateDTO {
    private String name;
    private String email;
    private BigInteger phone;
    private String lastname;
    private Date born_date;
    private GeneroEnum genero;
    private RoleEmployee roleEmployee;
    private AddressBasicDTO addressBasicDTO;
    private String password; // opcional, si se actualiza
}
