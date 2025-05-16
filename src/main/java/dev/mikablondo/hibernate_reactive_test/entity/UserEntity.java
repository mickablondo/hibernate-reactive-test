package dev.mikablondo.hibernate_reactive_test.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This class represents a User entity in the database.
 * It contains fields for the user's ID, name, surname, age, profession, and a set of associated UserLanguage entities.
 * The class is annotated with @Entity to indicate that it is a JPA entity.
 * It is also annotated with @Table to specify the table name in the database.
 */
@Entity
@Table(name = "utilisateur")
@Getter
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    private int age;

    private String metier;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserLanguageEntity> langages = new HashSet<>();

}
