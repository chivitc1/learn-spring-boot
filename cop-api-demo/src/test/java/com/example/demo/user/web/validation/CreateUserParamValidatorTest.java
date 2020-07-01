package com.example.demo.user.web.validation;

import com.example.demo.infrastructure.SpringProfiles;
import com.example.demo.user.User;
import com.example.demo.user.UserId;
import com.example.demo.user.UserService;
import com.example.demo.user.web.dto.CreateOfficerParams;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
@ActiveProfiles(SpringProfiles.TEST)
class CreateUserParamValidatorTest {

    @MockBean
    UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ValidatorFactory factory;

    @Test
    void emailAlreadyTaken_invalidUser() {
        String email = "user1@example.com";
        when(userService.findUserByEmail(email))
                .thenReturn(Optional.of(User.createOfficer(
                        new UserId(UUID.randomUUID()),
                        email,
                        encoder.encode("testing123")
                )));
        CreateOfficerParams params = new CreateOfficerParams();
        params.setEmail(email);
        params.setPassword("my-secret");

        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CreateOfficerParams>> violationSet = validator.validate(params);

        assertThat(violationSet).isNotEmpty();

    }
}