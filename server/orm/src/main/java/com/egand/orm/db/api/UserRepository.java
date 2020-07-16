package com.egand.orm.db.api;

import com.egand.orm.db.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findBySerialCode(String serialCode);
}
