package com.example.buensabor.entity;

import com.example.buensabor.Bases.BaseEntity;

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
}


