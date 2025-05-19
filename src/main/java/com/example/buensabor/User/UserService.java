package com.example.buensabor.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.buensabor.Bases.BaseRepository;
import com.example.buensabor.Bases.BaseServiceImplementation;
import com.example.buensabor.User.Interfaces.IUserService;

@Service
public class UserService extends BaseServiceImplementation<User, Long> implements IUserService{
    
    @Autowired
    public UserService(BaseRepository<User, Long> userRepository) {
        this.baseRepository = userRepository;
    }
    
}
