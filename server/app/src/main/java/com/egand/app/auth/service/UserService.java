package com.egand.app.auth.service;

import com.egand.app.auth.dto.user.RegisterCredentials;
import com.egand.app.auth.mapper.RoleMapper;
import com.egand.app.auth.mapper.UserMapper;
import com.egand.app.common.BaseService;
import com.egand.app.security.UserSec;
import com.egand.orm.db.api.RoleRepository;
import com.egand.orm.db.api.UserRepository;
import com.egand.orm.db.entities.Role;
import com.egand.orm.db.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Service
public class UserService extends BaseService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean alreadyExists(String serialCode) {
        Optional<User> user = userRepository.findBySerialCode(serialCode);
        return user.isPresent();
    }

    public User registerUser(RegisterCredentials credentials) throws RoleNotFoundException {
        Optional<Role> role = roleRepository.findRoleByCode(Role.USER);
        User user = UserMapper.INSTANCE.toUser(credentials);
        Role r = role.orElseThrow(RoleNotFoundException::new); // should't happen
        user.setRoleId(r.getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDefaultValues();
        userRepository.save(user);
        return user;
    }

    public Integer getUserId(String serialCode) {
        Optional<User> user = userRepository.findBySerialCode(serialCode);
        return user.map(User::getId).orElseThrow(() -> new UsernameNotFoundException(serialCode));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // this could be done in 1 step with a JOIN to the t_role table
        Optional<User> userFound = userRepository.findBySerialCode(s);
        User user = userFound.orElseThrow(() -> new UsernameNotFoundException(s));
        Optional<Role> roleFound = roleRepository.findRoleById(user.getId());
        Role role = roleFound.orElse(null);
        UserSec userSec = UserMapper.INSTANCE.toUserSec(user);
        userSec.setRole(RoleMapper.INSTANCE.toRoleSec(role));
        return userSec;
    }
}
