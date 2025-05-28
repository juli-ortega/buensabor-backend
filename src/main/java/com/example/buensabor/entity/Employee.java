package com.example.buensabor.entity;

import com.example.buensabor.entity.enums.RoleEmployee;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User{
    
    @Enumerated(EnumType.STRING)
    private RoleEmployee roleEmployee;

    @ManyToOne
    private Company company;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
