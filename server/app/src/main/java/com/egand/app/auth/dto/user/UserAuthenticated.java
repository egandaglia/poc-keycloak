package com.egand.app.auth.dto.user;

import com.egand.orm.db.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticated {

    private String serialCode;
    private String firstName;
    private String lastName;
    private Date dtLastAccess;
    private Role role;

}
