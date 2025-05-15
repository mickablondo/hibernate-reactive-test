package dev.mikablondo.hibernate_reactive_test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
    private String nom;
    private String prenom;
    private int age;
    private String metier;
}
