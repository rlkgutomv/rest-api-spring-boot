package br.edu.atitus.api_example.dtos;

import java.util.UUID;

public record RestauranteResponseDTO(
        UUID id,
        String description,
        double latitude,
        double longitude
) {}