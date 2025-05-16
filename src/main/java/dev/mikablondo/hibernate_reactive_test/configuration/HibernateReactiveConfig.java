package dev.mikablondo.hibernate_reactive_test.configuration;

import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.provider.ReactivePersistenceProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class HibernateReactiveConfig {

    @Value("${spring.r2dbc.url}")
    private String r2dbcUrl;

    @Value("${spring.r2dbc.username}")
    private String username;

    @Value("${spring.r2dbc.password}")
    private String password;

    @Bean
    public Mutiny.SessionFactory sessionFactory() {
        Map<String, Object> settings = new HashMap<>();
        settings.put("hibernate.connection.url", r2dbcUrl);
        settings.put("hibernate.connection.username", username);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.show_sql", true);
        settings.put("hibernate.format_sql", true);
        settings.put("hibernate.hbm2ddl.auto", "update");
        settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        PersistenceUnitInfo info = new SimplePersistenceUnitInfo(
                "my-persistence-unit",
                List.of("dev.mikablondo.hibernate_reactive_test.entity.UserEntity",
                        "dev.mikablondo.hibernate_reactive_test.entity.UserLanguageEntity",
                        "dev.mikablondo.hibernate_reactive_test.entity.LanguageEntity")
        );

        return new ReactivePersistenceProvider()
                .createContainerEntityManagerFactory(info, settings)
                .unwrap(Mutiny.SessionFactory.class);
    }
}

