package com.example.buensabor.Client;

import com.example.buensabor.User.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client extends User {
}
