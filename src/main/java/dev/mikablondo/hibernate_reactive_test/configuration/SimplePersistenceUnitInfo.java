package dev.mikablondo.hibernate_reactive_test.configuration;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;

import javax.sql.DataSource;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SimplePersistenceUnitInfo implements PersistenceUnitInfo {

    private final String persistenceUnitName;
    private final List<String> managedClassNames;

    public SimplePersistenceUnitInfo(String persistenceUnitName, List<String> managedClassNames) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
    }

    @Override public String getPersistenceUnitName() { return persistenceUnitName; }
    @Override public String getPersistenceProviderClassName() { return null; }

    @Override
    public String getScopeAnnotationName() {
        return "";
    }

    @Override
    public List<String> getQualifierAnnotationNames() {
        return List.of();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override public DataSource getJtaDataSource() { return null; }
    @Override public DataSource getNonJtaDataSource() { return null; }
    @Override public List<String> getMappingFileNames() { return Collections.emptyList(); }
    @Override public List<URL> getJarFileUrls() { return Collections.emptyList(); }
    @Override public URL getPersistenceUnitRootUrl() { return null; }
    @Override public List<String> getManagedClassNames() { return managedClassNames; }
    @Override public boolean excludeUnlistedClasses() { return false; }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.ENABLE_SELECTIVE;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    @Override public Properties getProperties() { return new Properties(); }
    @Override public String getPersistenceXMLSchemaVersion() { return "3.1"; }
    @Override public ClassLoader getClassLoader() { return Thread.currentThread().getContextClassLoader(); }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {

    }

    @Override public ClassLoader getNewTempClassLoader() { return getClassLoader(); }
}

