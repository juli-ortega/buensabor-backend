package com.example.buensabor.entity;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Province extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
