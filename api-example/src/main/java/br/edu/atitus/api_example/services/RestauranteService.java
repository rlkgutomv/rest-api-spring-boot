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

    public RestauranteEntity create(RestauranteRequestDTO dto, UserEntity user) {
        RestauranteEntity entity = new RestauranteEntity();
        entity.setDescription(dto.description());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());
        entity.setUser(user);
        return repository.save(entity);
    }

    public RestauranteEntity update(UUID id, RestauranteRequestDTO dto, UserEntity user) throws Exception {
        RestauranteEntity entity = repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new Exception("Restaurante não encontrado ou não pertence ao usuário"));

        entity.setDescription(dto.description());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());

        return repository.save(entity);
    }

    public List<RestauranteEntity> findAllByUser(UserEntity user) {
        return repository.findByUserId(user.getId());
    }

    public RestauranteEntity findByIdAndUser(UUID id, UserEntity user) throws Exception {
        return repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new Exception("Restaurante não encontrado ou não pertence ao usuário"));
    }

    public void delete(UUID id, UserEntity user) throws Exception {
        RestauranteEntity entity = repository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new Exception("Restaurante não encontrado ou não pertence ao usuário"));
        repository.delete(entity);
    }
}
