package com.example.buensabor.Articulo;
import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articulos_insumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloInsumo extends BaseEntity {
    
    @Column(name = "precio_compra")
    private double precioCompra;

    @Column(name = "stock_actual")
    private int stockActual;

    @Column(name = "stock_max")
    private int stockMax;
    
    @Column(name = "es_para_elaborar")
    private Boolean esParaElaborar;
}
