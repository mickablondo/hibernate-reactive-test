package dev.mikablondo.hibernate_reactive_test.services;

import dev.mikablondo.hibernate_reactive_test.dto.User;
import dev.mikablondo.hibernate_reactive_test.entity.UserEntity;
import dev.mikablondo.hibernate_reactive_test.repository.UserRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * This class is a service for managing User entities.
 * It provides methods to interact with the User repository.
 * The service is annotated with @Service to indicate that it is a Spring service.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * This method retrieves all users from the database.
     *
     * @return a Multi stream of User DTO objects
     */
    public Multi<User> getAllUsers() {
        return userRepository.findAll()
                .onItem().transform(userEntity -> User.builder()
                        .id(userEntity.getId())
                        .nom(userEntity.getNom())
                        .prenom(userEntity.getPrenom())
                        .age(userEntity.getAge())
                        .metier(userEntity.getMetier())
                        .build());
    }

    /**
     * This method creates a new user in the database.
     *
     * @param user the User DTO object to be created
     * @return a Uni<Void> indicating the completion of the operation
     */
    public Uni<Void> createUser(User user) {
        return userRepository.createUser(UserEntity.builder()
                        .id(UUID.randomUUID())
                        .nom(user.getNom())
                        .prenom(user.getPrenom())
                        .age(user.getAge())
                        .metier(user.getMetier())
                        .build());
    }

    /**
     * This method deletes a user from the database.
     *
     * @param id the ID of the user to be deleted
     * @return a Uni<Boolean> indicating if user is deleted successfully or not
     */
    public Uni<Boolean> deleteUser(UUID id) {
        return userRepository.deleteUser(id);
    }
}
