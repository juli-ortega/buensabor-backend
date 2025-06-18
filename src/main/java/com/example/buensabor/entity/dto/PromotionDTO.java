package com.example.buensabor.entity.dto;

import java.sql.Date;
import java.sql.Time;

import com.example.buensabor.Bases.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO extends BaseDTO {
    private CompanyDTO company;
    private String title;
    private Date dateFrom;
    private Date dateTo;
    private Time timeFrom;
    private Time timeTo;
    private String discountDescription;
    private double promotionalPrice;
    private PromotionTypeDTO promotionTypeDTO;
}
