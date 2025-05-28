package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Client;

@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {
    
}
