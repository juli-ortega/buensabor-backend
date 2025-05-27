package com.example.buensabor.Product;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{
    
    /*company_id int [ref: > company.id]
    description string
    estimated_time int
    price double
    image string
    child_subcategory_product_id int [unique, ref: > child_subcategory_product.id]*/
    
    @Column(name = "description")
    private String description;
    @Column(name = "estimated_time")
    private int estimatedTime;
    @Column(name = "price")
    private double price;
    @Column(name = "image")
    private String image;      
    
}