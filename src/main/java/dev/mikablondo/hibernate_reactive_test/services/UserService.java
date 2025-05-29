package dev.mikablondo.hibernate_reactive_test.services;

import dev.mikablondo.hibernate_reactive_test.dto.User;
import dev.mikablondo.hibernate_reactive_test.dto.UserFilter;
import dev.mikablondo.hibernate_reactive_test.dto.UserWithLangagesDTO;
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
    public Multi<User> getUsers(UserFilter filtre) {
        return userRepository.findByFiltre(filtre)
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

    /**
     * This method retrieves a user by its UUID from the database.
     *
     * @param uuid the UUID of the user to be retrieved
     * @return a Uni<User> containing the user DTO if found, or null if not found
     */
    public Uni<User> getUserById(UUID uuid) {
        return userRepository.findById(uuid)
                .onItem().transform(userEntity -> {
                    if (userEntity != null) {
                        return User.builder()
                                .id(userEntity.getId())
                                .nom(userEntity.getNom())
                                .prenom(userEntity.getPrenom())
                                .age(userEntity.getAge())
                                .metier(userEntity.getMetier())
                                .build();
                    } else {
                        return null;
                    }
                });
    }

    /**
     * This method retrieves users along with their associated languages and notes.
     *
     * @return a Multi stream of UserWithLangagesDTO objects
     */
    public Multi<UserWithLangagesDTO> getUsersWithNotes() {
        return userRepository.findUsersWithLangages()
                .onItem().transform(UserWithLangagesDTO::from);
    }
}
