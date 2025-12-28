package dev.mikablondo.hibernate_reactive_test.repository;

import dev.mikablondo.hibernate_reactive_test.entity.LanguageEntity;
import io.smallrye.mutiny.Multi;
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

    /**
     * This method retrieves all languages from the database.
     *
     * @return a Multi stream of LanguageEntity objects
     */
    public Multi<LanguageEntity> getAllLanguages() {
        return sessionFactory.withSession(session ->
                session.createQuery("FROM LanguageEntity", LanguageEntity.class)
                        .getResultList()
                        .onItem().transformToMulti(Multi.createFrom()::iterable)
                        .onItem().call(language -> session.fetch(language.getUtilisateurs()))
                        .collect().asList()
        ).onItem().transformToMulti(Multi.createFrom()::iterable);
    }

    /*public Multi<LanguageEntity> getAllLanguages2() {
        return sessionFactory.withSession(session ->
                session.createQuery("from LanguageEntity", LanguageEntity.class)
                        .getResults() // renvoie un Multi<Post> réactif
                        .convert().with(toFlux()) // transforme le Multi en Flux Reactor
        );
    }*/
}
