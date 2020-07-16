package com.egand.app.exceptions;

import javax.naming.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {

    public UserAlreadyExistsException() {
        super("The user associated with the provided serial code already exists. Please use a different serial code.");
    }
}
