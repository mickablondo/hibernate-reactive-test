package dev.mikablondo.hibernate_reactive_test.configuration;

import jakarta.persistence.spi.PersistenceUnitInfo;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.provider.ReactivePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class HibernateReactiveConfig {


    @Bean
    public Mutiny.SessionFactory sessionFactory() throws LiquibaseException {
        String host = TestContainerConfig.POSTGRES_CONTAINER.getHost();
        Integer port = TestContainerConfig.POSTGRES_CONTAINER.getMappedPort(5432);
        String jdbcUrl = String.format("jdbc:postgresql://%s:%d/testdb", host, port);

        // Appel Liquibase en JDBC
        runLiquibaseMigrations(jdbcUrl, "test", "test");

        // Hibernate reactive config
        Map<String, Object> settings = getStringObjectMap(host, port);

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

    /**
     * This method creates a map of settings for the Hibernate Reactive configuration.
     * @param host jdbc host
     * @param port jdbc port
     * @return a map of settings
     */
    private Map<String, Object> getStringObjectMap(String host, Integer port) {
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
        return settings;
    }

    /**
     * This method runs the Liquibase migrations using JDBC.
     * @param jdbcUrl the JDBC URL
     * @param username the username
     * @param password the password
     * @throws LiquibaseException if an error occurs while running Liquibase
     */
    private void runLiquibaseMigrations(String jdbcUrl, String username, String password) throws LiquibaseException {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.yml",
                    new ClassLoaderResourceAccessor(),
                    database
            );
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'exécution de Liquibase", e);
        }
    }
}
