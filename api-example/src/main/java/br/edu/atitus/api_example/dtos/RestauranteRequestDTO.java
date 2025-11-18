package br.edu.atitus.api_example.dtos;

public record RestauranteRequestDTO(
        String description,
        double latitude,
        double longitude
) {}