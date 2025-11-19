package br.edu.atitus.api_example.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Email ou senha inválidos"));
    }


    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredToken() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Token expirado. Faça login novamente."));
    }


    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> handleMalformedToken() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Token inválido ou corrompido."));
    }


    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<?> handleNoToken() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Autenticação necessária."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(Map.of("error", message));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
    }
}
