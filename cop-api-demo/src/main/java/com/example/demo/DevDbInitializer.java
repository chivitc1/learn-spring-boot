package com.example.demo;

import com.example.demo.infrastructure.SpringProfiles;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(SpringProfiles.DEV)
@RequiredArgsConstructor
public class DevDbInitializer implements ApplicationRunner {
    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createTestUsers();
    }

    private void createTestUsers() {
        userService.createOfficer("officer@example.com", "officer");
    }
}
