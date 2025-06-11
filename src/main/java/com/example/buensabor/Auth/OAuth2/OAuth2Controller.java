package com.example.buensabor.Auth.OAuth2;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @GetMapping("/google-login")
    public void loginWithGoogle(@RequestParam(required = false) String redirect, HttpServletResponse response) throws IOException {
        if (redirect != null) {
            Cookie cookie = new Cookie("redirect_url", redirect);
            cookie.setPath("/");
            cookie.setHttpOnly(true); // Podés poner true si querés más seguridad
            cookie.setMaxAge(300); // 5 minutos
            // cookie.setSecure(true); // solo si estás en HTTPS
            response.addCookie(cookie);
        }
        response.sendRedirect("/oauth2/authorization/google");
    }

}