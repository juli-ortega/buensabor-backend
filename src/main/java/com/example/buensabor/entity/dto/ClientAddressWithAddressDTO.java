package com.example.buensabor.entity.dto;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.dto.AddressDTOs.AddressBasicDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientAddressWithAddressDTO extends BaseDTO {
    private Long clientId;
    private AddressBasicDTO address;
}
