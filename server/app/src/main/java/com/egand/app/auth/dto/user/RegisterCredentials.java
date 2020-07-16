package com.egand.app.auth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCredentials {

    private String serialCode;
    private String firstName;
    private String lastName;
    private String password;

}
