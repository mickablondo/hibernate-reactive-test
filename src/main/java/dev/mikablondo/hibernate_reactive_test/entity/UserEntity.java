package dev.mikablondo.hibernate_reactive_test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class UserEntity {

    @Id
    private UUID id;

    private String name;
}
