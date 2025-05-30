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
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
public class OrderProduct extends BaseEntity {
    /* table order_product {
        id int [primary key]
        order_id int [ref: > order.id]
        product_id int [ref: > product.id]
        quantity int
        price double
    } */

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

}
