package com.example.buensabor.User;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.buensabor.Bases.BaseControllerImplementation;
import com.example.buensabor.User.Interfaces.IUserController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/user")
public class UserController extends BaseControllerImplementation<User, UserService> implements IUserController{
    
}
