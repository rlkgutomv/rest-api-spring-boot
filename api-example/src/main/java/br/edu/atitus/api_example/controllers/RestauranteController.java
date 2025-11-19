package br.edu.atitus.api_example.controllers;

import br.edu.atitus.api_example.dtos.RestauranteRequestDTO;
import br.edu.atitus.api_example.dtos.RestauranteResponseDTO;
import br.edu.atitus.api_example.entities.RestauranteEntity;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.services.RestauranteService;
import br.edu.atitus.api_example.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ws/restaurante")
public class RestauranteController {

    private final RestauranteService service;
    private final UserService userService;

    public RestauranteController(RestauranteService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    private UserEntity getLoggedUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return (UserEntity) userService.loadUserByUsername(email);
    }

    // CRIAR restaurante
    @PostMapping
    public RestauranteResponseDTO create(@RequestBody RestauranteRequestDTO dto) {
        UserEntity user = getLoggedUser();
        RestauranteEntity entity = service.create(dto, user);

        return new RestauranteResponseDTO(
                entity.getId(),
                entity.getDescription(),
                entity.getLatitude(),
                entity.getLongitude()
        );
    }

    // ATUALIZAR restaurante
    @PutMapping("/{id}")
    public RestauranteResponseDTO update(
            @PathVariable UUID id,
            @RequestBody RestauranteRequestDTO dto) throws Exception {

        UserEntity user = getLoggedUser();
        RestauranteEntity updated = service.update(id, dto, user);

        return new RestauranteResponseDTO(
                updated.getId(),
                updated.getDescription(),
                updated.getLatitude(),
                updated.getLongitude()
        );
    }

    // LISTAR restaurantes do usuário
    @GetMapping
    public List<RestauranteResponseDTO> getAll() {
        UserEntity user = getLoggedUser();
        return service.findAllByUser(user)
                .stream()
                .map(r -> new RestauranteResponseDTO(
                        r.getId(), r.getDescription(), r.getLatitude(), r.getLongitude()
                )).toList();
    }

    // BUSCAR restaurante por ID
    @GetMapping("/{id}")
    public RestauranteResponseDTO getById(@PathVariable UUID id) throws Exception {
        UserEntity user = getLoggedUser();
        RestauranteEntity r = service.findByIdAndUser(id, user);

        return new RestauranteResponseDTO(
                r.getId(), r.getDescription(), r.getLatitude(), r.getLongitude()
        );
    }

    // DELETAR restaurante (opcional)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) throws Exception {
        UserEntity user = getLoggedUser();
        service.delete(id, user);
        return "Restaurante removido com sucesso!";
    }
}
