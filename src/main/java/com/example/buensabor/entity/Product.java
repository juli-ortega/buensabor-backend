package com.example.buensabor.entity;

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
    
    @Column(name = "description")
    private String description;
    @Column(name = "estimated_time")
    private int estimatedTime;
    @Column(name = "price")
    private double price;
    @Column(name = "image")
    private String image;      
    
}