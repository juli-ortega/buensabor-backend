package com.example.buensabor.Articulo;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articulos_manufacturados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloManufacturado extends BaseEntity {
    
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tiempo_estimado_minutos")
    private int tiempoEstimadoMinutos;

    @Column(name = "preparacion")
    private String preparacion;

}
