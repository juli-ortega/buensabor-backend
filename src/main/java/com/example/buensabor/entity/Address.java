package com.example.buensabor.Address;

import com.example.buensabor.Bases.BaseEntity;
import com.example.buensabor.enums.OwnerType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String street;
    private Integer number;
    private Integer postalCode;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}

