package com.example.buensabor.entity.dto.AddressDTOs;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.dto.CityDTOs.CityBasicDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressBasicDTO extends BaseDTO {
    private String street;
    private Integer number;
    private Integer postalCode;
    private CityBasicDTO city;
}
