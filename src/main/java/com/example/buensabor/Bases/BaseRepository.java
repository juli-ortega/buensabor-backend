package com.example.buensabor.Bases;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository <E extends BaseEntity, ID extends Serializable> extends JpaRepository<E, ID> {
    
}
