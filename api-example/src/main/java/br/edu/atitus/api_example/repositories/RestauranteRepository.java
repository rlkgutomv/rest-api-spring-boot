package br.edu.atitus.api_example.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.api_example.entities.RestauranteEntity;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, UUID> {

    List<RestauranteEntity> findByUserId(UUID userId);

    Optional<RestauranteEntity> findByIdAndUserId(UUID id, UUID userId);
}
