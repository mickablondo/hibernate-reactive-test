package dev.mikablondo.hibernate_reactive_test.application.configuration;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateReactiveConfig {

    @Bean
    public Mutiny.SessionFactory sessionFactory() {
        // TODO : use @Value to inject the properties
        Map<String, Object> settings = new HashMap<>();
        settings.put("jakarta.persistence.jdbc.url", System.getProperty("DB_URL"));
        settings.put("jakarta.persistence.jdbc.user", System.getProperty("DB_USERNAME"));
        settings.put("jakarta.persistence.jdbc.password", System.getProperty("DB_PASSWORD"));
        settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        settings.put("hibernate.show_sql", true);
        settings.put("hibernate.format_sql", true);
        settings.put("hibernate.hbm2ddl.auto", "update");

        @SuppressWarnings("resource")
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit", settings);
        return emf.unwrap(Mutiny.SessionFactory.class);
    }
}

