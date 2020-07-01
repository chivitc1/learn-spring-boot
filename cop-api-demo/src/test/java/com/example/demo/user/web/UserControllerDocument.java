package com.example.demo.user.web;

import com.example.demo.infrastructure.test.CopControllerTest;
import com.example.demo.user.UserService;
import com.example.demo.user.Users;
import com.example.demo.user.web.dto.CreateOfficerParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static com.example.demo.infrastructure.security.SecurityHelperForMockMvc.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@CopControllerTest(UserController.class)
public class UserControllerDocument {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RestDocumentationContextProvider restDocumentation;

    private RestDocumentationResultHandler resultHandler;

    @BeforeEach
    public void setup() {
        resultHandler = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint(),
                        removeMatchingHeaders("X.*", "Pragma", "Expires")));
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .apply(springSecurity())
                .alwaysDo(resultHandler)
                .build();
    }

    @Test
    public void ownUserDetailsWhenNotLoggedInExample() throws Exception {
        mvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authenOfficerDetails() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);

        when(service.getUser(Users.officer().getId())).thenReturn(Optional.of(Users.officer()));

        mvc.perform(get("/api/users/me")
                .header(HEADER_AUTHORIZATION, bearer(accessToken)))
                .andExpect(status().isOk())
                .andDo(resultHandler.document(
                        responseFields(
                                fieldWithPath("id").description("The unique id of the user."),
                                fieldWithPath("email").description("The email address of the user."),
                                fieldWithPath("roles").description("The security roles of the user."))));
    }

    @Test
    public void createOfficer() throws Exception {
        String email = "user1@example.com";
        String password = "my-secret";

        CreateOfficerParams params = new CreateOfficerParams();
        params.setEmail(email);
        params.setPassword(password);

        when(service.createOfficer(email, password)).thenReturn(Users.newOfficer(email, password));

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isCreated())
                .andDo(resultHandler.document(
                        requestFields(
                                fieldWithPath("email").description("The email address of user to be created."),
                                fieldWithPath("password").description("The password for the new user.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("The unique id of the user."),
                                fieldWithPath("email").description("The email address of the user."),
                                fieldWithPath("roles").description("The security roles of the user."))
                        )
                );
    }
}
