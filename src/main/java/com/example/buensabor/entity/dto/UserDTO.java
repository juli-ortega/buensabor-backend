package com.example.buensabor.entity.dto;

import java.math.BigInteger;
import java.sql.Date;

import com.example.buensabor.Bases.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends BaseDTO {
    private String name;
    private String email;
    private String password;
    private BigInteger phone;
}