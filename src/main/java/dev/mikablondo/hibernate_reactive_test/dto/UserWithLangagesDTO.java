package dev.mikablondo.hibernate_reactive_test.dto;

import dev.mikablondo.hibernate_reactive_test.entity.UserEntity;

import java.util.List;
import java.util.UUID;

/**
 * This class represents a User DTO with programming languages and their associated notes.
 * It contains fields for the user's ID, name, surname, age, profession, and a list of programming languages with notes.
 * The class is immutable and uses a record to define its structure.
 */
public record UserWithLangagesDTO(
        UUID id,
        String nom,
        String prenom,
        Integer age,
        String metier,
        List<LanguageNoteDTO> langages
) {
    /**
     * Converts a UserEntity to a UserWithLangagesDTO.
     *
     * @param entity the UserEntity to convert
     * @return a UserWithLangagesDTO containing the user's details and their programming languages with notes
     */
    public static UserWithLangagesDTO from(UserEntity entity) {
        return new UserWithLangagesDTO(
                entity.getId(),
                entity.getNom(),
                entity.getPrenom(),
                entity.getAge(),
                entity.getMetier(),
                entity.getLangages().stream()
                        .map(LanguageNoteDTO::from)
                        .toList()
        );
    }
}



