package br.edu.atitus.api_example.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String extractUsername(String token) {
        // TODO: implemente de verdade (claims)
        return "user@example.com"; // placeholder para testes
    }

    public boolean isTokenValid(String token, UserDetails user) {
        // TODO: validar assinatura/expiração e subject == user.getUsername()
        return true; // placeholder
    }
}