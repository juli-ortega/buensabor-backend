package com.example.buensabor.Articulo;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articulos_manufacturado_detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloManufacturadoDetalle extends BaseEntity {
    
    @Column(name = "cantidad")
    private int cantidad;

}
