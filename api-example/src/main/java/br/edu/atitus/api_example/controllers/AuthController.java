package br.edu.atitus.api_example.controllers;

import br.edu.atitus.api_example.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.get("email"), body.get("password"))
        );

        UserDetails user = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(user);

        return Map.of(
                "token", token,
                "email", user.getUsername()
        );
    }
}
