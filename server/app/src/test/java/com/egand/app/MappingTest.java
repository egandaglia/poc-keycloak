package com.egand.app;

import static org.assertj.core.api.Assertions.assertThat;

import com.egand.app.auth.dto.user.LoginCredentials;
import com.egand.orm.db.entities.User;
import com.egand.app.auth.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MappingTest {

    @Test
    void contextLoads() {
        User user = new User("U0I9533", "Digo", "Ciao", "pwd");
        LoginCredentials credentials = UserMapper.INSTANCE.toLoginCredentials(user);
        assertThat(credentials).isNotNull();
        assertThat(credentials.getPassword()).isEqualTo(user.getPassword());
        assertThat(credentials.getSerialCode()).isEqualTo(user.getSerialCode());
    }
}
