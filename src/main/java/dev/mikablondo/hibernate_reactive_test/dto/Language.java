package dev.mikablondo.hibernate_reactive_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

/**
 * This class represents a Language DTO (Data Transfer Object).
 * It contains fields for the language's ID, name, and a set of associated User entities.
 * The class is annotated with @Builder to enable the builder pattern for object creation.
 * It is also annotated with @Data to generate getters, setters, equals, hashCode, and toString methods.
 */
@Builder
@Data
@AllArgsConstructor
public class Language {
    private UUID id;
    private String nom;
    private Set<User> utilisateurs;
}
