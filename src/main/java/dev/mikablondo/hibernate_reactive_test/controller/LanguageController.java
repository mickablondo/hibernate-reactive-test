package dev.mikablondo.hibernate_reactive_test.controller;

import dev.mikablondo.hibernate_reactive_test.dto.Language;
import dev.mikablondo.hibernate_reactive_test.services.LanguageService;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a REST controller for managing Language entities.
 * It provides an endpoint to retrieve all languages from the database.
 * The controller is annotated with @RestController to indicate that it is a Spring MVC controller.
 */
@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping
    public Uni<ResponseEntity<Void>> createLanguage(@RequestBody Language language) {
        return languageService.createLanguage(language)
                .map(v -> ResponseEntity.status(HttpStatus.CREATED).build());
    }
}
