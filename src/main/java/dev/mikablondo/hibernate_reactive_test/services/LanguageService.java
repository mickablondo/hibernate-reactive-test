package dev.mikablondo.hibernate_reactive_test.services;

import dev.mikablondo.hibernate_reactive_test.dto.Language;
import dev.mikablondo.hibernate_reactive_test.entity.LanguageEntity;
import dev.mikablondo.hibernate_reactive_test.repository.LanguageRepository;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * This class is a service for managing Language entities.
 * It provides methods to interact with the Language repository.
 * The service is annotated with @Service to indicate that it is a Spring service.
 */
@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;

    /**
     * This method creates a new language in the database.
     *
     * @param language the Language DTO object to be created
     * @return a Uni<Void> indicating the completion of the operation
     */
    public Uni<Void> createLanguage(Language language) {
        return languageRepository.createLanguage(LanguageEntity.builder()
                .id(UUID.randomUUID())
                .nom(language.getNom())
                .build());
    }
}
