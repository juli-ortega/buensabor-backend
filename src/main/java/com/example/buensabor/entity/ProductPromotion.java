package com.example.buensabor.entity;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_promotion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPromotion extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Column(name = "value")
    private Double value; // valor según el tipo: precio fijo, % descuento o X para promos tipo 2x1

    @Column(name = "extra_value")
    private Double extraValue; // para el "Y" en promos tipo X_FOR_Y (ej: 2x1 sería 2 y 1)
}


