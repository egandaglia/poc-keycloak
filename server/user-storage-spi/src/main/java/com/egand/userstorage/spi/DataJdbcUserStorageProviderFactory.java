package com.egand.userstorage.spi;

import com.egand.orm.db.config.DatabaseConfig;
import com.egand.userstorage.spi.DataJdbcUserStorageProvider;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

public class DataJdbcUserStorageProviderFactory implements UserStorageProviderFactory<DataJdbcUserStorageProvider> {

    private static final String PROVIDER_NAME = "user-storage-spi-spring-data-jdbc";
    private static final Logger logger = Logger.getLogger(DataJdbcUserStorageProviderFactory.class);

    @Override
    public DataJdbcUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
        DataSource ds = null;
        JndiTemplate jndi = new JndiTemplate();
        Connection c = null;
        try {
            logger.info("envcontext creato");
            ds = jndi.lookup( "java:jboss/datasources/postgresDS" , DataSource.class);
            c = ds.getConnection();
            logger.info("connection eseguita");
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
        DataJdbcUserStorageProvider provider = new DataJdbcUserStorageProvider(c);
        provider.setModel(componentModel);
        provider.setSession(keycloakSession);
        return provider;
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }
}
