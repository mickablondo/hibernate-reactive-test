package dev.mikablondo.hibernate_reactive_test.repository;

import dev.mikablondo.hibernate_reactive_test.entity.UserEntity;
import io.smallrye.mutiny.Multi;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;

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
}
