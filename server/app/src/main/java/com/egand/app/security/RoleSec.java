package com.egand.app.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class RoleSec implements GrantedAuthority {

    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }
}
