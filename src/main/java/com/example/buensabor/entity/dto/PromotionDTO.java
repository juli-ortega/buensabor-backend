package com.example.buensabor.entity.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

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
    private Long companyId;
    private String title;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalTime timeFrom;
    private LocalTime timeTo;    
    private Set<DayOfWeek> dayOfWeeks;
    private String discountDescription;
    private PromotionTypeDTO promotionTypeDTO;
    private List<ProductPromotionDTO> productPromotions;
}
