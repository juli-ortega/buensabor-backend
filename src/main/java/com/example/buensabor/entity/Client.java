package com.example.buensabor.entity;

import com.example.buensabor.entity.enums.GeneroEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private GeneroEnum genero;
}
