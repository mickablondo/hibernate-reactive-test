package dev.mikablondo.hibernate_reactive_test.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class represents a UserLanguage entity in the database.
 * It contains fields for the associated User and Language entities, as well as a rating (note).
 * The class is annotated with @Entity to indicate that it is a JPA entity.
 * It is also annotated with @Table to specify the table name in the database.
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "utilisateur_langage")
@IdClass(UserLanguageId.class)
public class UserLanguageEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private UserEntity utilisateur;

    @Id
    @ManyToOne
    @JoinColumn(name = "langage_id", nullable = false)
    private LanguageEntity langage;

    private Integer note;
}
