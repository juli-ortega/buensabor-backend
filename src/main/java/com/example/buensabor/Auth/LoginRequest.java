package com.example.buensabor.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
