package com.example.buensabor.Auth.OAuth2;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.buensabor.Auth.JWT.JWTService;
import com.example.buensabor.entity.Client;
import com.example.buensabor.entity.Company;
import com.example.buensabor.entity.Employee;
import com.example.buensabor.entity.User;
import com.example.buensabor.repository.ClientRepository;
import com.example.buensabor.repository.CompanyRepository;
import com.example.buensabor.repository.EmployeeRepository;
import com.example.buensabor.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired private JWTService jwtService;
    @Autowired private UserRepository userRepository;
    @Autowired private CompanyRepository companyRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private EmployeeRepository employeeRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        Optional<User> existingUser = userRepository.findByEmail(email);
        User user;

        if (existingUser.isPresent()) {
            user = existingUser.get();
        } else {
            String userType = request.getParameter("userType");
            if (userType == null) userType = "client"; // default

            switch (userType.toLowerCase()) {
                case "company":
                    Company company = new Company();
                    company.setEmail(email);
                    company.setName(name);
                    company.setPassword(encoder.encode(UUID.randomUUID().toString()));
                    companyRepository.save(company);
                    user = company;
                    break;
                case "employee":
                    Employee employee = new Employee();
                    employee.setEmail(email);
                    employee.setName(name);
                    employee.setPassword(encoder.encode(UUID.randomUUID().toString()));
                    employeeRepository.save(employee);
                    user = employee;
                    break;
                default:
                    Client client = new Client();
                    client.setEmail(email);
                    client.setName(name);
                    client.setPassword(encoder.encode(UUID.randomUUID().toString()));
                    clientRepository.save(client);
                    user = client;
            }
        }

        // Genero token
        String token = jwtService.generateToken(user);

        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Solo en HTTPS, para testing local podés dejarlo en false
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 día

        // acá agregás la cookie al HttpServletResponse
        response.addCookie(cookie);

        // Leer cookie redirect_uri
        String redirectUri = null;
        if (request.getCookies() != null) {
            for (Cookie cookie2 : request.getCookies()) {
                if (cookie2.getName().equals("redirect_url")) {
                    redirectUri = cookie2.getValue();
                    break;
                }
            }
        }

        response.sendRedirect(redirectUri);
    }

}
