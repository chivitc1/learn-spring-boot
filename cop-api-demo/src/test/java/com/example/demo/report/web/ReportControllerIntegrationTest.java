package com.example.demo.report.web;

import com.example.demo.infrastructure.SpringProfiles;
import com.example.demo.infrastructure.security.SecurityHelperForRestAssured;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import com.example.demo.user.Users;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(SpringProfiles.INTEGRATION_TEST)
public class ReportControllerIntegrationTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository repository;

    @BeforeEach
    public void setup() {
        RestAssured.port = serverPort;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void officerIsUnableToPostAReportIfFileSizeIsTooBig() {
        userService.createOfficer(Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
        String dateTime = "2018-04-11T22:59:03.189+02:00";
        String description = "The suspect is wearing a black hat.";

        SecurityHelperForRestAssured.givenAuthenticatedUser(
                serverPort, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD)
                .when()
                .multiPart("file",
                        new MultiPartSpecBuilder(new byte[2_000_000])
                        .fileName("picture.png")
                        .mimeType("image/png").build())
                .formParam("dateTime", dateTime)
                .formParam("description", description)
                .post("/api/reports")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @AfterEach
    public void teardown() {
        repository.deleteAll();
    }
}
