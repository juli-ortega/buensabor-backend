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
        System.out.println("Session ID antes de guardar redirect: " + request.getSession().getId());
System.out.println("Redirect param: " + redirect);
        // ✅ Guardamos el redirect en la sesión correctamente
        if (redirect != null) {
            request.getSession().setAttribute("redirect_url", redirect);
            System.out.println("Guardado en sesión: " + request.getSession().getAttribute("redirect_url"));
        }

        // ✅ (Opcional) Cookie accesible desde el frontend, solo para depurar o verificar desde JS
        if (redirect != null) {
            ResponseCookie cookie = ResponseCookie.from("redirect_uri", redirect)
                    .path("/")
                    .httpOnly(false)  // accesible por JS (opcional)
                    .secure(false)    // ponelo en true si usás HTTPS
                    .sameSite("Lax")
                    .maxAge(60)       // 1 minuto
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        }

        // 🔁 Redirigir al login de Google
        response.sendRedirect("/oauth2/authorization/google");
    }
}
