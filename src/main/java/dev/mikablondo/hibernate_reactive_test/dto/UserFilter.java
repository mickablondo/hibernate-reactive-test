package dev.mikablondo.hibernate_reactive_test.dto;

import lombok.Data;

@Data
public class UserFilter {
    private String nom;
    private String prenom;
    private Integer ageMin;
    private Integer ageMax;
    private String metier;
}
