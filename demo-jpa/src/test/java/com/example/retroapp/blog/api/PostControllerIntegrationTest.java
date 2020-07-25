package com.example.retroapp.blog.api;

import com.example.retroapp.blog.api.dto.PostDto;
import com.example.retroapp.infra.constants.SpringProfiles;
import com.example.retroapp.repositories.PostRepository;
import com.example.retroapp.services.PostService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(SpringProfiles.INTEGRATION_TEST)
class PostControllerIntegrationTest {

    @LocalServerPort
    int serverPort;

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void setup() {
        RestAssured.port = serverPort;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void postArticle() {
        PostDto postDto = PostDto.builder()
                .author("test_author")
                .title("test article")
                .content("test some content")
                .build();
        postService.postArticle(postDto);

        RestAssured.with()
                .body(postDto)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(URI.create("/api/articles"))
                .then()
                .statusCode(201);
    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
    }
}