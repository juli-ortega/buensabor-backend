package com.example.buensabor.entity.dto;

import com.example.buensabor.Bases.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientAddressDTO extends BaseDTO {
    private ClientDTO client;
    private AddressDTO address;
}
