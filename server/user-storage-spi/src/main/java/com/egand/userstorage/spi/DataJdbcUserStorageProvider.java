package com.egand.userstorage.spi;

import com.egand.orm.db.api.UserRepository;
import com.egand.orm.db.config.DatabaseConfig;
import com.egand.orm.db.entities.User;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.cache.OnUserCache;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserRegistrationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactory;
import org.springframework.data.jdbc.repository.support.SimpleJdbcRepository;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

@Component
public class DataJdbcUserStorageProvider implements
        UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator,
        UserRegistrationProvider,
        OnUserCache
{
    private static final Logger logger = Logger.getLogger(DataJdbcUserStorageProvider.class);
    public static final String PASSWORD_CACHE_KEY = UserAdapter.class.getName() + ".password";
    public static final String USER_ADAPTER_CACHE_KEY = UserAdapter.class.getName();
    private KeycloakSession session;

    private final Connection connection;
    private ComponentModel model;

    public DataJdbcUserStorageProvider(Connection connection) {
        this.connection = connection;
    }

    public void setModel(ComponentModel model) {
        this.model = model;
    }

    public void setSession(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return CredentialModel.PASSWORD.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realmModel, UserModel userModel, String credentialType) {
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realmModel, UserModel userModel, CredentialInput credentialInput) {
        if (!supportsCredentialType(credentialInput.getType())) {
            logger.info("credentialType:" + credentialInput.getType() + " not supported");
            return false;
        }
        if(!(credentialInput instanceof UserCredentialModel)) {
            logger.info("credential Input not instanceof userCredentialModel");
            return false;
        }
        UserCredentialModel cred = (UserCredentialModel)credentialInput;
        String password = getPassword(userModel);
        UserAdapter userAdapter = getUserAdapter(userModel);
        logger.info("user password: " + password);
        logger.info("credentialInput: " + cred.getValue());
        logger.info("algorithm: " + cred.getAlgorithm());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(password != null && encoder.matches(cred.getValue(), password)) {
            userAdapter.setUserAttributes();
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public UserModel getUserById(String id, RealmModel realmModel) {
        logger.info("getUserById: " + id);
        StorageId storageId = new StorageId(id);
        String username = storageId.getExternalId();
        logger.info("getUserById: username from id : " + username);
        return getUserByUsername(username, realmModel);
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realmModel) {
        logger.info("getUserByUsername: " + username);
        List<User> userList = new ArrayList<User>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM t_user WHERE LOWER(serial_code) = LOWER(?)");
            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                User u = new User();
                u.setSerialCode(rs.getString("serial_code"));
                u.setPassword(rs.getString("password"));
                u.setDtIns(rs.getDate("dt_ins"));
                u.setDtLastAccess(rs.getDate("dt_last_access"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                userList.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(userList.isEmpty()) {
            logger.info("getUserByUsername: could not find user with serialCode " + username);
            return null;
        }
        User user = userList.get(0);
        logger.info("getUserByUsername: user found - " + user.toString());
        return new UserAdapter(session, realmModel, model, user);
    }

    @Override
    public UserModel getUserByEmail(String s, RealmModel realmModel) {
        return null;
    }

    @Override
    public UserModel addUser(RealmModel realmModel, String s) {
        return null;
    }

    @Override
    public boolean removeUser(RealmModel realmModel, UserModel userModel) {
        return false;
    }

    public String getPassword(UserModel user) {
        logger.info("getPassword: " + user.toString());
        String password = null;
        if (user instanceof CachedUserModel) {
            logger.info("user is instance of CacheUserModel");
            UserAdapter u = (UserAdapter) ((CachedUserModel) user).getCachedWith().get(USER_ADAPTER_CACHE_KEY);
            password = u.getPassword();
        }
        else if (user instanceof UserAdapter) {
            logger.info("user is instance of UserAdapter");
            password = ((UserAdapter)user).getPassword();
        }
        return password;
    }

    public UserAdapter getUserAdapter(UserModel user) {
        if (user instanceof CachedUserModel) {
            logger.info("user is instance of CacheUserModel");
            return (UserAdapter) ((CachedUserModel) user).getCachedWith().get(USER_ADAPTER_CACHE_KEY);
        }
        else if (user instanceof UserAdapter) {
            logger.info("user is instance of UserAdapter");
            return (UserAdapter) user;
        }
        else return null;
    }

    @Override
    public void onCache(RealmModel realmModel, CachedUserModel cachedUserModel, UserModel userModel) {
        logger.info("CACHING IN CORSO!!!");
        String password = ((UserAdapter)userModel).getPassword();
        if (password != null) {
            cachedUserModel.getCachedWith().put(USER_ADAPTER_CACHE_KEY, userModel);
        }
    }
}
