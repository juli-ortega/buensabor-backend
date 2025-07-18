package com.example.buensabor.entity.dto;

import com.example.buensabor.Bases.BaseDTO;
import com.example.buensabor.entity.enums.PromotionBehavior;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionTypeDTO extends BaseDTO{
    private String name;
    private PromotionBehavior behavior;
    private Long companyId;
}
