package com.example.buensabor.entity;

import com.example.buensabor.Bases.BaseEntity;
import com.example.buensabor.entity.enums.UnitMeasure;

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
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "unit_measure")
    private UnitMeasure unitMeasure;

    @Column(name = "status")
    private boolean status;

    @Column(name = "min_stock")
    private double minStock;

    @Column(name = "current_stock")
    private double currentStock;

    @Column(name = "max_stock")
    private double maxStock;

    @ManyToOne
    @JoinColumn(name = "categoryIngredient_id")
    private CategoryIngredient categoryIngredient;
}