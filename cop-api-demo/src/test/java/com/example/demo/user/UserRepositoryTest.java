package com.example.demo.user;

import com.example.demo.entitybase.InMemoryUniqueIdGenerator;
import com.example.demo.entitybase.UniqueIdGenerator;
import com.example.demo.infrastructure.SpringProfiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles(SpringProfiles.TEST)
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void storeUser() {
        HashSet<UserRole> roles = new HashSet<>();
        roles.add(UserRole.OFFICER);
        User user = repository.save(User.builder()
                .id(repository.nextId())
                .email("a@example.com")
                .password("secret")
                .roles(roles)
                .build());

        assertThat(user).isNotNull();
    }

    @Test
    public void findByEmail() {

        HashSet<UserRole> roles = new HashSet<>();
        roles.add(UserRole.OFFICER);
        User user = repository.save(User.builder()
                .id(repository.nextId())
                .email("a@example.com")
                .password("secret")
                .roles(roles)
                .build());
        Optional<User> optUser = repository.findByEmailIgnoreCase(user.getEmail());

        assertThat(optUser).isNotEmpty()
                .contains(user);
    }

    @Test
    public void findByEmail_ignoreCase() {

        HashSet<UserRole> roles = new HashSet<>();
        roles.add(UserRole.OFFICER);
        User user = repository.save(User.builder()
                .id(repository.nextId())
                .email("AA@Example.com")
                .password("secret")
                .roles(roles)
                .build());
        Optional<User> optUser = repository.findByEmailIgnoreCase(user.getEmail());

        assertThat(optUser).isNotEmpty()
                .contains(user);
    }

    @Test
    public void findByEmail_unkownEmail() {

        HashSet<UserRole> roles = new HashSet<>();
        roles.add(UserRole.OFFICER);
        User user = repository.save(User.builder()
                .id(repository.nextId())
                .email("aaa@example.com")
                .password("secret")
                .roles(roles)
                .build());
        Optional<User> optUser = repository.findByEmailIgnoreCase("bbb@example.com");

        assertThat(optUser).isEmpty();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UniqueIdGenerator<UUID> generator() {
            return new InMemoryUniqueIdGenerator();
        }
    }
}