package com.example.buensabor.Address;

import com.example.buensabor.Bases.BaseEntity;
import com.example.buensabor.Client.Client;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public  class Address extends BaseEntity {

    private String street;
    private Integer number;
    private Integer postalCode;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}

