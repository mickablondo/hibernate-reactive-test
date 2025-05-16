package dev.mikablondo.hibernate_reactive_test.configuration;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * This class configures a PostgreSQL container for testing purposes.
 * It initializes the container and sets system properties for database connection.
 * TODO : utiliser @Value pour injecter les propriétés
 */
public class TestContainerConfig {

    public static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        POSTGRES_CONTAINER.start();

        System.setProperty("DB_URL", POSTGRES_CONTAINER.getJdbcUrl());
        System.setProperty("DB_USERNAME", POSTGRES_CONTAINER.getUsername());
        System.setProperty("DB_PASSWORD", POSTGRES_CONTAINER.getPassword());
    }

    public static void init() {
        // Pour forcer le chargement de la classe si besoin
    }
}

