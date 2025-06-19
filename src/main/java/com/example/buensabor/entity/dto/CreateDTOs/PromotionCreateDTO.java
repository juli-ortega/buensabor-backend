package com.example.buensabor.entity.dto.CreateDTOs;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionCreateDTO {
    private String title;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private String discountDescription;
    private double promotionalPrice;
    private Long promotionTypeId;
    private Set<DayOfWeek> dayOfWeeks;
    private List<Long> productIds;
}
