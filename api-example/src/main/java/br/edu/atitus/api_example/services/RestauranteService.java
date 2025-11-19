package br.edu.atitus.api_example.services;

import br.edu.atitus.api_example.dtos.RestauranteRequestDTO;
import br.edu.atitus.api_example.entities.RestauranteEntity;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestauranteService {

    private final RestauranteRepository repository;

    public RestauranteService(RestauranteRepository repository) {
        this.repository = repository;
    }

    // ===============================
    // CRIAR RESTAURANTE
    // ===============================
    public RestauranteEntity create(RestauranteRequestDTO dto, UserEntity user) {

        if (dto.description() == null || dto.description().trim().isEmpty()) {
            throw new RuntimeException("A descrição é obrigatória.");
        }

        if (dto.description().length() < 3) {
            throw new RuntimeException("A descrição deve ter no mínimo 3 caracteres.");
        }

        if (dto.latitude() < -90 || dto.latitude() > 90) {
            throw new RuntimeException("Latitude inválida. Deve estar entre -90 e 90.");
        }

        if (dto.longitude() < -180 || dto.longitude() > 180) {
            throw new RuntimeException("Longitude inválida. Deve estar entre -180 e 180.");
        }

        boolean localDuplicado = repository.findByUserId(user.getId())
                .stream()
                .anyMatch(r ->
                        r.getLatitude() == dto.latitude() &&
                                r.getLongitude() == dto.longitude()
                );

        if (localDuplicado) {
            throw new RuntimeException("Você já cadastrou um restaurante nesse mesmo local.");
        }

        RestauranteEntity entity = new RestauranteEntity();
        entity.setDescription(dto.description());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());
        entity.setUser(user);

        return repository.save(entity);
    }

    // ===============================
    // ATUALIZAR RESTAURANTE
    // ===============================
    public RestauranteEntity update(UUID id, RestauranteRequestDTO dto, UserEntity user) {

        RestauranteEntity entity = repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado ou não pertence ao usuário."));

        if (dto.description() == null || dto.description().trim().isEmpty()) {
            throw new RuntimeException("A descrição é obrigatória.");
        }

        if (dto.description().length() < 3) {
            throw new RuntimeException("A descrição deve ter no mínimo 3 caracteres.");
        }

        if (dto.latitude() < -90 || dto.latitude() > 90) {
            throw new RuntimeException("Latitude inválida. Deve estar entre -90 e 90.");
        }

        if (dto.longitude() < -180 || dto.longitude() > 180) {
            throw new RuntimeException("Longitude inválida. Deve estar entre -180 e 180.");
        }

        boolean localDuplicado = repository.findByUserId(user.getId())
                .stream()
                .anyMatch(r ->
                        !r.getId().equals(id) &&
                                r.getLatitude() == dto.latitude() &&
                                r.getLongitude() == dto.longitude()
                );

        if (localDuplicado) {
            throw new RuntimeException("Você já possui outro restaurante nesse local.");
        }

        entity.setDescription(dto.description());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());

        return repository.save(entity);
    }

    // ===============================
    // LISTAR RESTAURANTES DO USUÁRIO
    // ===============================
    public List<RestauranteEntity> findAllByUser(UserEntity user) {
        return repository.findByUserId(user.getId());
    }

    // ===============================
    // BUSCAR POR ID (do usuário)
    // ===============================
    public RestauranteEntity findByIdAndUser(UUID id, UserEntity user) {
        return repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado ou não pertence ao usuário."));
    }

    // ===============================
    // DELETAR RESTAURANTE
    // ===============================
    public void delete(UUID id, UserEntity user) {
        RestauranteEntity entity = repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado ou não pertence ao usuário."));

        repository.delete(entity);
    }
}
