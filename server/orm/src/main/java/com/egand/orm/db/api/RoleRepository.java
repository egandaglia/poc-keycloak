package com.egand.orm.db.api;

import com.egand.orm.db.entities.Role;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends Repository<Role, Integer> {

    @Query("select * from t_role where role = :role")
    Optional<Role> findRoleByCode(@Param("role") String role);

    @Query("select * from t_role where id = :id")
    Optional<Role> findRoleById(@Param("id") Integer id);
}
