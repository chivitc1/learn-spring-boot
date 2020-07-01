package com.example.demo.user;

import com.example.demo.entitybase.InMemoryUniqueIdGenerator;
import com.example.demo.entitybase.UniqueIdGenerator;
import com.example.demo.infrastructure.SpringProfiles;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(SpringProfiles.INTEGRATION_TEST)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void saveUser() {
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.OFFICER);
        User user = repository.save(new User(repository.nextId(),
                "user1@example.com",
                "my-secret",
                roles));
        assertThat(user).isNotNull();
        assertThat(repository.count()).isEqualTo(1L);

        entityManager.flush();
        assertThat(jdbcTemplate.queryForObject("SELECT count(*) FROM cop_user", Long.class)).isEqualTo(1L);
        assertThat(jdbcTemplate.queryForObject("SELECT count(*) FROM user_roles", Long.class)).isEqualTo(1L);
        assertThat(jdbcTemplate.queryForObject("SELECT roles FROM user_roles", String.class)).isEqualTo("OFFICER");
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UniqueIdGenerator uniqueIdGenerator() {
            return new InMemoryUniqueIdGenerator();
        }
    }
}
