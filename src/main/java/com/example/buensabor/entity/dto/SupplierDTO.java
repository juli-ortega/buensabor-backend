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
public class SupplierDTO extends BaseDTO {
    private CompanyDTO company;
    private String name;
    private String phone;
    private String email;
    private AddressDTO address;
}
