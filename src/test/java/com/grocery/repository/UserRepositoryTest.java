package com.grocery.repository;

import com.grocery.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository undertest;

    @AfterEach
    void tearDown() {
        undertest.deleteAll();
    }

    @Test
    void findById() {
        //given
        String email = "vineet@gmail.com";
        User user = new User("vineet",email,
                "12345678","vineet",
                "tiwari","shiv nagar",
                "8982210201","1","1",
                "buyer");
        undertest.save(user);
        //when
        boolean exists = undertest.existsByEmail(email);

        //then
        assertThat(exists).isTrue();
    }

    @Test
    void findByUsername() {
        String username = "vineet";
        User user = new User("vineet","vineet@gmail.com",
                "12345678","vineet",
                "tiwari","shiv nagar",
                "8982210201","1","1",
                "buyer");
        undertest.save(user);
        //when
        boolean exists = undertest.existsByUsername(username);

        //then
        assertThat(exists).isTrue();
    }

}
