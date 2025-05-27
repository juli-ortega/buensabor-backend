package com.example.buensabor.Address;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Province extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
