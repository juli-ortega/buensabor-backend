package com.example.buensabor.entity.dto.CreateDTOs;

import com.example.buensabor.entity.enums.PromotionBehavior;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionTypeCreateDTO{
    private String name;
    private PromotionBehavior behavior;
}
