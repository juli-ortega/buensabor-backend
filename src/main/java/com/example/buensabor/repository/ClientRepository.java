package com.example.buensabor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Company;

@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {
    
    @Query("SELECT DISTINCT c FROM Client c " +
       "JOIN Order o ON o.client = c " +
       "WHERE o.company = :company AND (c.isActive IS NULL OR c.isActive = true)")
    List<Client> findClientsWithOrdersByCompany(@Param("company") Company company);
}
