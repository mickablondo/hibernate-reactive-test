package dev.mikablondo.hibernate_reactive_test.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This class represents a Language entity in the database.
 * It contains fields for the language's ID, name, and a set of associated UserLanguage entities.
 * The class is annotated with @Entity to indicate that it is a JPA entity.
 * It is also annotated with @Table to specify the table name in the database.
 */
@Entity
@Builder
@Table(name = "langage")
public class LanguageEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "langage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserLanguageEntity> utilisateurs = new HashSet<>();
}
