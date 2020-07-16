package com.egand.orm.db.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@Table("t_user")
public class User {

    @Id
    private Integer id;

    @Column("serial_code")
    private String serialCode;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String password;

    private boolean enabled;

    @Column("dt_ins")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dtIns;

    @Column("dt_last_access")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dtLastAccess;

    @Column("role_id")
    private Integer roleId;

    @Transient
    private Role role;

    public User(String serialCode, String firstName, String lastName, String password) {
        this.serialCode = serialCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        setDefaultValues();
    }

    public User(Integer id, String serialCode, String firstName, String lastName, String password, boolean enabled, Date dtIns, Date dtLastAccess) {
        this.id = id;
        this.serialCode = serialCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.enabled = enabled;
        this.dtIns = dtIns;
        this.dtLastAccess = dtLastAccess;
    }

    public void setDefaultValues() {
        this.id = null;
        this.enabled = true;
        this.dtIns = new Date();
        this.dtLastAccess = new Date();
    }
}
