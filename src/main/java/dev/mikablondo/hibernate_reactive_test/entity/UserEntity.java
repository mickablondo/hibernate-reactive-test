package dev.mikablondo.hibernate_reactive_test.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
