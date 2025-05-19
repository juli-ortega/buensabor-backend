package com.example.buensabor.Articulo;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articulos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Articulo extends BaseEntity {
    
    @Column(name = "denominacion")
    private String denominacion;

    @Column(name = "precio_venta")
    private double precioVenta;

}
