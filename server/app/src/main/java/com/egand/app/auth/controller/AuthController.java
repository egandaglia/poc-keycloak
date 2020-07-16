package com.egand.app.auth.controller;

import com.egand.app.auth.dto.user.UserAuthenticated;
import com.egand.app.auth.mapper.UserMapper;
import com.egand.app.common.BaseController;
import com.egand.app.auth.dto.user.LoginCredentials;
import com.egand.app.auth.dto.user.RegisterCredentials;
import com.egand.app.auth.service.UserService;
import com.egand.orm.db.entities.User;
import com.egand.app.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping(value = "/auth")
public class AuthController extends BaseController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserAuthenticated> login(@RequestBody LoginCredentials loginCredentials) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginCredentials.getSerialCode(), loginCredentials.getPassword()));
        if(!auth.isAuthenticated()) {
            throw new BadCredentialsException("Login failed. Invalid serial code or password");
        }
        User user = (User) auth.getPrincipal();
        UserAuthenticated userAuthenticated = UserMapper.INSTANCE.toUserAuthenticated(user);
        return ResponseEntity.ok(userAuthenticated);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserAuthenticated> register(@RequestBody RegisterCredentials credentials) throws UserAlreadyExistsException, RoleNotFoundException {
        if(userService.alreadyExists(credentials.getSerialCode())) {
            throw new UserAlreadyExistsException();
        }
        User user = userService.registerUser(credentials);
        UserAuthenticated userAuthenticated = UserMapper.INSTANCE.toUserAuthenticated(user);
        return ResponseEntity.ok(userAuthenticated);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
