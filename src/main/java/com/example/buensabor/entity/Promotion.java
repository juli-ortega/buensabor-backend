package com.example.buensabor.entity;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion extends BaseEntity {
  
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "title")
    private String title;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "time_from")
    private LocalTime timeFrom;

    @Column(name = "time_to")
    private LocalTime timeTo;

    @ElementCollection
    @CollectionTable(name = "promotion_days", joinColumns = @JoinColumn(name = "promotion_id"))
    @Column(name = "day_of_week")
    private Set<DayOfWeek> dayOfWeeks;

    @Column(name = "discount_description")
    private String discountDescription;

    @Column(name = "promotional_price")
    private double promotionalPrice;
    
    @ManyToOne
    @JoinColumn(name = "promotion_type_id")
    private PromotionType promotionType;
}
