package dev.mikablondo.hibernate_reactive_test.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "langage")
public class LanguageEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "langage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserLanguageEntity> utilisateurs = new HashSet<>();
}
