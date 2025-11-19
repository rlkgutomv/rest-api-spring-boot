package br.edu.atitus.api_example.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public UserEntity save(UserEntity user) throws Exception {

        if (repository.existsByEmail(user.getEmail()))
            throw new Exception("Já existe usuário cadastrado com este e-mail");


        user.setPassword(encoder.encode(user.getPassword()));

        return repository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Não existe usuário com este email"));
    }
}