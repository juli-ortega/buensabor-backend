package com.example.buensabor.entity;

import com.example.buensabor.Bases.BaseEntity;
import com.example.buensabor.entity.enums.UnitMeasure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Ingredient extends BaseEntity{
    /* 
     table ingredient {
  id int [primary key]
  company_id int [ref: > company.id]
  name string
  price decimal
  quantity decimal
  unit_measure unit_measure
  status boolean
  min_stock decimal
  current_stock decimal
  max_stock decimal
  ingredient_subcategory_id int [unique, ref: > ingredient_subcategory.id]
}
      */
    // @Column(name = "company_id")
    // private int companyId;
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
    // @Column(name = "ingredient_subcategory_id")
    // private int ingredientSubcategoryId;
}