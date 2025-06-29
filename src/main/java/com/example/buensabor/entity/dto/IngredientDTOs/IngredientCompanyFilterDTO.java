package com.example.buensabor.entity.dto.IngredientDTOs;

import com.example.buensabor.Bases.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCompanyFilterDTO extends BaseDTO {
    private BaseDTO companyId;
}
