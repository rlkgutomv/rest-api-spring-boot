package br.edu.atitus.api_example.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupDTO(

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Email é obrigatório")
        @Pattern(
                regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$",
                message = "Email inválido. Use um formato como nome@dominio.com ou nome@dominio.com.br"
        )
        String email,

        @NotBlank(message = "Senha obrigatória")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
                message = "Senha fraca. Deve conter: letra maiúscula, letra minúscula, número e mínimo 8 caracteres."
        )
        String password
) {}