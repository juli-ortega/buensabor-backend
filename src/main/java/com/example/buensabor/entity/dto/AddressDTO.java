package com.example.buensabor.entity.dto;

import com.example.buensabor.Bases.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO extends BaseDTO {
    private String street;
    private Integer number;
    private Integer postalCode;
    private CityDTO city;  
}
