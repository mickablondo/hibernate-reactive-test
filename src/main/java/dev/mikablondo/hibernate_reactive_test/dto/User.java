package dev.mikablondo.hibernate_reactive_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * This class represents a User DTO (Data Transfer Object).
 * It contains fields for the user's name, surname, age, and profession.
 * The class is annotated with @Data to generate getters, setters, and other utility methods.
 * It is also annotated with @Builder to provide a builder pattern for creating instances.
 */
@Data
@Builder
@AllArgsConstructor
public class User {
    private String nom;
    private String prenom;
    private int age;
    private String metier;
}
