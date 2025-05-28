package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Address;

@Repository
public interface AddressRepository extends BaseRepository<Address, Long> {
    
}