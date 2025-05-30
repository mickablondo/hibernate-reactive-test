package dev.mikablondo.hibernate_reactive_test.dto;

import dev.mikablondo.hibernate_reactive_test.entity.LanguageEntity;
import dev.mikablondo.hibernate_reactive_test.entity.UserLanguageEntity;

import java.util.UUID;

/**
 * This class represents a Data Transfer Object (DTO) for a programming language with its associated note.
 * It contains fields for the language's ID, name, and note.
 * The class is immutable and uses a record to define its structure.
 */
public record LanguageNoteDTO(UUID id, String nom, Integer note) {
    /**
     * Converts a LangageEntity to a LangageNoteDTO.
     *
     * @param entity the LangageEntity to convert
     * @return a LangageNoteDTO containing the language's ID, name, and note
     */
    public static LanguageNoteDTO from(UserLanguageEntity entity) {
        LanguageEntity langage = entity.getLangage();
        return new LanguageNoteDTO(
                langage.getId(),
                langage.getNom(),
                entity.getNote()
        );
    }
}
