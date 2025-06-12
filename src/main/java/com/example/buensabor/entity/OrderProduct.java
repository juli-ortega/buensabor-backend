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
@Table(name = "order_products")
@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
public class OrderProduct extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "clarifications")
    private String clarifications;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

}
