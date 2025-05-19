package com.example.buensabor.User;

import org.springframework.stereotype.Repository;

import com.example.buensabor.Bases.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    
}
