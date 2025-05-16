package dev.mikablondo.hibernate_reactive_test.configuration;

import jakarta.persistence.spi.PersistenceUnitInfo;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.provider.ReactivePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class HibernateReactiveConfig {


    @Bean
    public Mutiny.SessionFactory sessionFactory() {
        String host = TestContainerConfig.POSTGRES_CONTAINER.getHost();
        Integer port = TestContainerConfig.POSTGRES_CONTAINER.getMappedPort(5432);
        String r2dbcUrl = String.format("r2dbc:postgresql://%s:%d/testdb", host, port);

        Map<String, Object> settings = new HashMap<>();
        settings.put("hibernate.connection.url", r2dbcUrl.replaceFirst("^r2dbc:", ""));
        settings.put("hibernate.connection.username", "test");
        settings.put("hibernate.connection.password", "test");
        settings.put("hibernate.show_sql", true);
        settings.put("hibernate.format_sql", true);
        settings.put("hibernate.hbm2ddl.auto", "update");
        settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        settings.put("hibernate.connection.pool_size", 5);

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

