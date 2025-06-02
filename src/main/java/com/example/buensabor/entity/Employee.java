package com.example.buensabor.entity;

import java.sql.Date;

import com.example.buensabor.entity.enums.GeneroEnum;
import com.example.buensabor.entity.enums.RoleEmployee;

import jakarta.persistence.Column;
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
    @Column(name = "lastname")
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private GeneroEnum genero;

    @Column(name = "born_date")
    private Date born_date;
    
    @Enumerated(EnumType.STRING)
    private RoleEmployee roleEmployee;

    @ManyToOne
    private Company company;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;
}
