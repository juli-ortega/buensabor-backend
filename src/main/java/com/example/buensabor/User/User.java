package com.example.buensabor.User;

import java.math.BigInteger;
import java.sql.Date;

import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    
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
