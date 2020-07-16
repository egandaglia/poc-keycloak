package main.java.com.egand.pockeycloak.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
/*
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object token = authentication.getCredentials();
        return Optional.ofNullable(token)
                .flatMap(t -> Optional.of(userAuthenticationService.authenticateByToken(String.valueOf(t)))
                        .map(u -> User.builder()
                        .username(u.getSerialCode())
                        .password(u.getPassword())
                        .roles(u.getRoleId().toString())
                        .build()))
                .orElseThrow(() -> new BadCredentialsException("Invalid authentication token = " + token));
    }
}*/
