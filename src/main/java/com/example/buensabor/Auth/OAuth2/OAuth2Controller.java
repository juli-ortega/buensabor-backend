package com.example.buensabor.Auth.OAuth2;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {

    @GetMapping("/google-login")
    public void loginWithGoogle(@RequestParam(required = false) String redirect,
                            HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        
        // Usar la sesión en lugar de cookies para almacenar el redirect
        if (redirect != null) {
            request.getSession().setAttribute("redirect_url", redirect);
            System.out.println("SESSIOJNNN: " + request.getSession().getAttribute(redirect));
        }
        
        // Opcional: cookie adicional para el frontend
        if (redirect != null) {
            ResponseCookie cookie = ResponseCookie.from("redirect_uri", redirect)
                    .path("/")
                    .httpOnly(false)
                    .maxAge(60) // 1 minuto (solo para frontend)
                    .sameSite("Lax")
                    .secure(false)
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }
        
        response.sendRedirect("/oauth2/authorization/google");
    }

}