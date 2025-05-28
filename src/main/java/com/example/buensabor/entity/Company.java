package com.example.buensabor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company extends User{

    @Column(name = "cuit")
    private String cuit;
    
    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
