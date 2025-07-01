package com.example.buensabor.repository;

import org.springframework.stereotype.Repository;
import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.ClientAddress;

@Repository
public interface ClientAddressRepository extends BaseRepository<ClientAddress, Long> {
}
