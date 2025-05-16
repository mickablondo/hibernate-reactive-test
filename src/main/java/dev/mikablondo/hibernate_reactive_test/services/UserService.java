package dev.mikablondo.hibernate_reactive_test.services;

import dev.mikablondo.hibernate_reactive_test.dto.User;
import dev.mikablondo.hibernate_reactive_test.repository.UserRepository;
import io.smallrye.mutiny.Multi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * This class is a service for managing User entities.
 * It provides methods to interact with the User repository.
 * The service is annotated with @Service to indicate that it is a Spring service.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Multi<User> getAllUsers() {
        return userRepository.findAll()
                .onItem().transform(userEntity -> User.builder()
                        .nom(userEntity.getNom())
                        .prenom(userEntity.getPrenom())
                        .age(userEntity.getAge())
                        .metier(userEntity.getMetier())
                        .build());
    }
}
