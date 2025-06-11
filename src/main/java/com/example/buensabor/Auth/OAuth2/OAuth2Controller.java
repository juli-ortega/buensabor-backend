package com.example.buensabor.Auth.OAuth2;

import java.io.IOException;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @GetMapping("/google-login")
    public void loginWithGoogle(@RequestParam(required = false) String redirect, HttpServletResponse response) throws IOException {
        if (redirect != null) {
            ResponseCookie cookie = ResponseCookie.from("redirect_url", redirect)
                .path("/")
                .httpOnly(false)
                .maxAge(300) // 5 minutos
                .build();
            response.addHeader("Set-Cookie", cookie.toString());
        }
        response.sendRedirect("/oauth2/authorization/google");
    }

}