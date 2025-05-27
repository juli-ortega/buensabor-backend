package com.example.buensabor.Client;

import com.example.buensabor.Address.Address;
import com.example.buensabor.Bases.BaseEntity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ClientAddress extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
