package com.example.buensabor.entity;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public  class Address extends BaseEntity {
    
    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @Column(name = "postalCode")
    private Integer postalCode;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}

