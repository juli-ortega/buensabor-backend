package com.example.buensabor.entity.dto.ResponseDTOs;

import java.math.BigInteger;

import com.example.buensabor.Bases.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO extends BaseDTO {
    private String name;
    private String email;
    private String role;
    private BigInteger phone;
}
