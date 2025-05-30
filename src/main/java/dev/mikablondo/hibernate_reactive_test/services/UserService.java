package dev.mikablondo.hibernate_reactive_test.services;

import dev.mikablondo.hibernate_reactive_test.dto.NoteDTO;
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

    /**
     * This method retrieves a user with its associated languages and notes by its ID.
     *
     * @param id the ID of the user to be retrieved
     * @return a Uni<UserWithLangagesDTO> containing the user with languages and notes
     */
    public Uni<UserWithLangagesDTO> getUserWithNotes(String id) {
        return userRepository.findUserWithLangages(id)
                .onItem().transform(UserWithLangagesDTO::from);
    }

    /**
     * This method adds a note for a specific user and language.
     *
     * @param userId      the ID of the user
     * @param languageId  the ID of the language
     * @param note        the note to be added
     * @return a Uni<Void> indicating the completion of the operation
     */
    public Uni<Void> addNote(String userId, String languageId, NoteDTO note) {
        validateNote(note);
        UUID userUUID = UUID.fromString(userId);
        UUID languageUUID = UUID.fromString(languageId);
        return userRepository.addOrUpdateNote(userUUID, languageUUID, note.note());
    }

    /**
     * This method validates the note to ensure it is within the acceptable range.
     *
     * @param note the NoteDTO object to be validated
     * @throws IllegalArgumentException if the note is invalid
     */
    private void validateNote(NoteDTO note) {
        if (note == null || note.note() == null || note.note() < 0 || note.note() > 10) {
            throw new IllegalArgumentException("Note invalide : doit être entre 0 et 10");
        }
    }
}
