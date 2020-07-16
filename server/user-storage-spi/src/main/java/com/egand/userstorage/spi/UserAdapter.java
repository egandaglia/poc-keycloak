package com.egand.userstorage.spi;

import com.egand.orm.db.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class UserAdapter extends AbstractUserAdapterFederatedStorage {

    private User user;
    private String keycloakId;

    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel) {
        super(session, realm, storageProviderModel);
    }

    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel, User user) {
        super(session, realm, storageProviderModel);
        this.user = user;
        this.keycloakId = StorageId.keycloakId(storageProviderModel, user.getSerialCode());
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void setUsername(String s) {
        this.user.setSerialCode(s);
    }

    public void setUserAttributes() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        setSingleAttribute("serialCode", this.user.getSerialCode());
        setSingleAttribute("firstName", this.user.getFirstName());
        setSingleAttribute("lastName", this.user.getLastName());
        setSingleAttribute("dtIns", df.format(this.user.getDtIns()));
        setSingleAttribute("dtLastAccess", df.format(this.user.getDtLastAccess()));
    }

    public String getPassword() {
        return this.user.getPassword();
    }

    public void setPassword(String password) {
        this.user.setPassword(password);
    }

    @Override
    public String getId() {
        return keycloakId;
    }
}
