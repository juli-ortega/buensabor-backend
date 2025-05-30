package com.example.buensabor.entity.dto;

import java.sql.Date;

import com.example.buensabor.Bases.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyIngredientDTO extends BaseDTO {
    private Date dateBuy;
    private CompanyDTO company;
    private IngredientDTO ingredient;
    private SupplierDTO supplier;
    private double quantity;
    private double priceByUnit;
}
