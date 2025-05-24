package com.example.buensabor.User;

import java.math.BigInteger;
import java.sql.Date;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // o SINGLE_TABLE, TABLE_PER_CLASS
public abstract class User extends BaseEntity {
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "born_date")
    private Date born_date;

    @Column(name = "phone")
    private BigInteger phone;

}
