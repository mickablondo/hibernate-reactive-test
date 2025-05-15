package dev.mikablondo.hibernate_reactive_test.entity;

import jakarta.persistence.*;

@Entity
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
