package com.example.buensabor.entity;

import java.util.List;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
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

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "estimated_time")
    private int estimatedTime;
    @Column(name = "price")
    private double price;
    @Column(name = "image")
    private String image;
    @Column(name = "profit_percentage")
    private int profit_percentage;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductIngredient> productIngredients;

    // isActive ya está en BaseEntity
}