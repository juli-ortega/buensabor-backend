package com.example.buensabor.entity;
import java.sql.Time;
import java.sql.Date;

import com.example.buensabor.Bases.BaseEntity;
import com.example.buensabor.entity.enums.PromotionTipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    /*table promotion {
  id int [primary key]
  company_id int [ref: > company.id]
  title string
  date_from date
  date_to date
  time_from time
  time_to time
  discount_description string
  promotional_price double
  promotion_type_id int [ref: > promotion_type.id]
} */
    @ManyToOne
    @Column(name = "company_id")
    private Company company;

    @Column(name = "title")
    private String title;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @Column(name = "time_from")
    private Time timeFrom;

    @Column(name = "time_to")
    private Time timeTo;

    @Column(name = "discount_description")
    private String discountDescription;

    @Column(name = "promotional_price")
    private double promotionalPrice;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_type_id")
    private PromotionTipe promotionTypeEnum;
}
