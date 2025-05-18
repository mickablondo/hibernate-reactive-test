package dev.mikablondo.hibernate_reactive_test.repository;

import dev.mikablondo.hibernate_reactive_test.entity.UserEntity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;

/**
 * This class is a repository for managing UserEntity objects.
 * It uses Hibernate Reactive to perform database operations in a non-blocking way.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository.
 */
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final Mutiny.SessionFactory sessionFactory;

    /**
     * This method retrieves all users from the database.
     *
     * @return a Multi stream of UserEntity objects
     */
    public Multi<UserEntity> findAll() {
        return sessionFactory.withSession(session ->
                session.createQuery("from UserEntity", UserEntity.class)
                        .getResultList()
        ).onItem().transformToMulti(users -> Multi.createFrom().iterable(users));
    }

    /**
     * This method creates a new user in the database.
     *
     * @param entity the UserEntity object to be created
     * @return a Uni<Void> indicating the completion of the operation
     */
    public Uni<Void> createUser(UserEntity entity) {
        return sessionFactory.withTransaction((session, tx) ->
                session.persist(entity)
                        .onItem().invoke(() -> System.out.println("User created: " + entity))
                        .onFailure().invoke((Throwable t) -> System.err.println("Failed to create user: " + t.getMessage()))
        ).replaceWithVoid();
    }
}
