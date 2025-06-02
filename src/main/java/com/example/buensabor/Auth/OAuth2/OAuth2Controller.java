package com.example.buensabor.Auth.OAuth2;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @GetMapping("/success")
    public ResponseEntity<?> oauth2Success(@RequestParam String token) {
        return ResponseEntity.ok(Map.of(
            "token", token,
            "message", "Login successful via OAuth2"
        ));
    }
}