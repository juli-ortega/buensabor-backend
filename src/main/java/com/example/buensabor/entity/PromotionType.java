package com.example.buensabor.entity;

import com.example.buensabor.Bases.BaseEntity;
import com.example.buensabor.entity.enums.PromotionBehavior;

import jakarta.persistence.Column;
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
@Table(name = "promotions_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromotionType extends BaseEntity {
    
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "behavior")
    private PromotionBehavior behavior;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
