package com.example.buensabor.Products.ProductIngredient;

import com.example.buensabor.Bases.BaseEntity;
import com.example.buensabor.Products.Product.Product;
import com.example.buensabor.Products.Ingredient.Ingredient;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductIngredient extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "quantity", nullable = false)
    private double quantity;
}
