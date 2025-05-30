package com.example.buensabor.entity;

import java.sql.Date;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "buy_ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyIngredient extends BaseEntity {
    /* table buy_ingredient {
  id int [primary key]
  dateBuy datetime
  company_id int [ref: > company.id]
  ingredient_id int [ref: > ingredient.id]
  supplier_id int [ref: > supplier.id]
  quantity decimal
  pricebyunit decimal
}*/
    @Column(name = "date_buy")
    private Date dateBuy;

    @ManyToOne
    @Column(name = "company_id")
    private Company company;

    @ManyToOne
    @Column(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @Column(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "quantity")
    private double quantity;
    
    @Column(name = "price_by_unit")
    private double priceByUnit;

}
