package com.example.demo.infrastructure.test;

import com.example.demo.infrastructure.SpringProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest
@ContextConfiguration(classes = CopControllerTestConfiguration.class)
@ActiveProfiles(SpringProfiles.TEST)
public @interface CopControllerTest {
    @AliasFor(annotation = WebMvcTest.class, attribute = "value")
    Class<?> [] value() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?> [] controllers() default {};
}
