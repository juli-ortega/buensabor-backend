package com.example.buensabor.Employee;

import com.example.buensabor.Address.Address;
import com.example.buensabor.Company.Company;
import com.example.buensabor.User.User;
import com.example.buensabor.enums.RoleEmployee;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee extends User{
    
    @Enumerated(EnumType.STRING)
    private RoleEmployee roleEmployee;

    @ManyToOne
    private Company company;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
