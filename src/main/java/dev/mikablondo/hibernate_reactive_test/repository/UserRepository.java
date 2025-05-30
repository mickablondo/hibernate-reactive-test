package dev.mikablondo.hibernate_reactive_test.repository;

import com.github.dockerjava.api.exception.NotFoundException;
import dev.mikablondo.hibernate_reactive_test.dto.UserFilter;
import dev.mikablondo.hibernate_reactive_test.entity.LanguageEntity;
import dev.mikablondo.hibernate_reactive_test.entity.UserEntity;
import dev.mikablondo.hibernate_reactive_test.entity.UserLanguageEntity;
import dev.mikablondo.hibernate_reactive_test.entity.UserLanguageId;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.stereotype.Repository;

import java.util.UUID;

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
     * @param filtre the filter criteria for retrieving users
     * @return a Multi stream of UserEntity objects
     */
    public Multi<UserEntity> findByFiltre(UserFilter filtre) {
        return sessionFactory.withSession(session -> {
            StringBuilder query = new StringBuilder("from UserEntity where 1=1");
            if (filtre.getNom() != null) query.append(" and lower(nom) = :nom");
            if (filtre.getPrenom() != null) query.append(" and lower(prenom) = :prenom");
            if (filtre.getAgeMin() != null) query.append(" and age >= :ageMin");
            if (filtre.getAgeMax() != null) query.append(" and age <= :ageMax");
            if (filtre.getMetier() != null) query.append(" and lower(metier) = :metier");

            var hQuery = session.createQuery(query.toString(), UserEntity.class);
            if (filtre.getNom() != null) hQuery.setParameter("nom", filtre.getNom().toLowerCase());
            if (filtre.getPrenom() != null) hQuery.setParameter("prenom", filtre.getPrenom().toLowerCase());
            if (filtre.getAgeMin() != null) hQuery.setParameter("ageMin", filtre.getAgeMin());
            if (filtre.getAgeMax() != null) hQuery.setParameter("ageMax", filtre.getAgeMax());
            if (filtre.getMetier() != null) hQuery.setParameter("metier", filtre.getMetier().toLowerCase());

            return hQuery.getResultList();
        }).onItem().transformToMulti(users -> Multi.createFrom().iterable(users));
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

    /**
     * This method retrieves a user by its UUID from the database.
     *
     * @param uuid the UUID of the user to be retrieved
     * @return a Uni<UserEntity> containing the user entity if found, or null if not found
     */
    public Uni<UserEntity> findById(UUID uuid) {
        return sessionFactory.withTransaction((session, tx) ->
                session.find(UserEntity.class, uuid)
                        .onItem().invoke(user -> System.out.println("User found: " + user))
                        .onFailure().invoke((Throwable t) -> System.err.println("Failed to find user: " + t.getMessage()))
        );
    }

    /**
     * This method deletes a user by its ID from the database.
     *
     * @param userId the ID of the user to be deleted
     * @return a Uni<Boolean> indicating if user is deleted successfully or not
     */
    public Uni<Boolean> deleteUser(UUID userId) {
        return sessionFactory.withTransaction((session, tx) ->
                session.find(UserEntity.class, userId)
                        .onItem().ifNotNull().transformToUni(user -> session.remove(user).replaceWith(true))
                        .onItem().ifNull().continueWith(false)
        );
    }

    /**
     * This method retrieves all users with their associated programming languages and notes.
     *
     * @return a Multi stream of UserEntity objects with their languages and notes
     */
    public Multi<UserEntity> findUsersWithLangages() {
        return sessionFactory.withSession(session ->
                session.createQuery("""
                                    select distinct u
                                    from UserEntity u
                                    left join fetch u.langages ul
                                    left join fetch ul.langage
                                """, UserEntity.class)
                        .getResultList()
        ).onItem().transformToMulti(Multi.createFrom()::iterable);
    }

    /**
     * This method retrieves a user with its associated programming languages and notes.
     *
     * @return a Uni<UserEntity> containing the user entity with its languages and notes
     */
    public Uni<UserEntity> findUserWithLangages(String id) {
        return sessionFactory.withSession(session ->
                session.createQuery("""
                                    select distinct u
                                    from UserEntity u
                                    left join fetch u.langages ul
                                    left join fetch ul.langage
                                    where u.id = :id
                                """, UserEntity.class)
                        .setParameter("id", UUID.fromString(id))
                        .getSingleResultOrNull()
        ).onItem().ifNotNull().transformToUni(user -> Uni.createFrom().item(user));
    }

    /**
     * This method adds or updates a note for a user's programming language.
     *
     * @param userId      the ID of the user
     * @param languageId  the ID of the programming language
     * @param note        the note to be added or updated
     * @return a Uni<Void> indicating the completion of the operation
     */
    public Uni<Void> addOrUpdateNote(UUID userId, UUID languageId, Integer note) {
        return sessionFactory.withTransaction((session, tx) ->
                session.find(UserEntity.class, userId)
                        .onItem().ifNull().failWith(() -> new NotFoundException("User not found"))
                        .flatMap(user ->
                                session.find(LanguageEntity.class, languageId)
                                        .onItem().ifNull().failWith(() -> new NotFoundException("Language not found"))
                                        .flatMap(language -> {
                                            UserLanguageId id = new UserLanguageId(userId, languageId);
                                            return session.find(UserLanguageEntity.class, id)
                                                    .flatMap(existing -> {
                                                        if (existing != null) {
                                                            return session.remove(existing)
                                                                    .chain(() -> session.persist(UserLanguageEntity.withNote(user, language, note)));
                                                        } else {
                                                            return session.persist(UserLanguageEntity.withNote(user, language, note));
                                                        }
                                                    });
                                        })
                        )
        );
    }
}
