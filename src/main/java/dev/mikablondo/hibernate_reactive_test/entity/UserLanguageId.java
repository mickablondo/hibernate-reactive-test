package dev.mikablondo.hibernate_reactive_test.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * This class represents a composite key for the UserLanguage entity.
 * It implements Serializable to allow it to be used as a primary key in JPA.
 * The class contains two fields: utilisateur and langage, which are both UUIDs.
 * It also overrides the equals and hashCode methods to ensure proper comparison and hashing.
 */
public class UserLanguageId implements Serializable {
    private UUID utilisateur;
    private UUID langage;

    public UserLanguageId() {}

    public UserLanguageId(UUID utilisateur, UUID langage) {
        this.utilisateur = utilisateur;
        this.langage = langage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLanguageId that)) return false;
        return Objects.equals(utilisateur, that.utilisateur) &&
                Objects.equals(langage, that.langage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utilisateur, langage);
    }
}
