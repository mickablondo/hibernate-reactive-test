package dev.mikablondo.hibernate_reactive_test.repository;

import dev.mikablondo.hibernate_reactive_test.entity.LanguageEntity;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LanguageRepository {

    private final Mutiny.SessionFactory sessionFactory;

    /**
     * This method creates a new language in the database.
     *
     * @param entity the LanguageEntity object to be created
     * @return a Uni<Void> indicating the completion of the operation
     */
    public Uni<Void> createLanguage(LanguageEntity entity) {
        return sessionFactory.withTransaction((session, tx) ->
                session.persist(entity)
                        .onItem().invoke(() -> System.out.println("Language created: " + entity))
                        .onFailure().invoke((Throwable t) -> System.err.println("Failed to create language: " + t.getMessage()))
        );
    }
}
