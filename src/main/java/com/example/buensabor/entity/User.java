package com.example.buensabor.entity;

import java.math.BigInteger;

import com.example.buensabor.Auth.Roles.Roles;
import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)  // o SINGLE_TABLE, TABLE_PER_CLASS
public abstract class User extends BaseEntity {
    
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private BigInteger phone;

    @Enumerated(EnumType.STRING)
    private Roles role;

}
