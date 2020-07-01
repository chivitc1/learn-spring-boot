package com.example.demo.user.web;

import com.example.demo.infrastructure.test.CopControllerTest;
import com.example.demo.user.UserService;
import com.example.demo.user.Users;
import com.example.demo.user.web.dto.CreateOfficerParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.example.demo.infrastructure.security.SecurityHelperForMockMvc.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@CopControllerTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOfficer() throws Exception{
        String email = "user1@example.com";
        String password = "my-secret";

        CreateOfficerParams params = new CreateOfficerParams();
        params.setEmail(email);
        params.setPassword(password);

        when(service.createOfficer(email, password))
                .thenReturn(Users.newOfficer(email, password));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("roles").isArray())
                .andExpect(jsonPath("roles[0]").value("OFFICER"));
        verify(service).createOfficer(email, password);
    }

    @Test
    public void givenNotAuthenticated_whenAskingCurrentUserDetails_forbidden() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenAuthenticatedAsOfficer_whenAskingCurrentUserDetails_detailsReturned() throws Exception {
        String accessToken = obtainAccessToken(mockMvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);

        when(service.getUser(Users.officer().getId())).thenReturn(Optional.of(Users.officer()));

        mockMvc.perform(get("/api/users/me")
            .header(HEADER_AUTHORIZATION, bearer(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").value(Users.OFFICER_EMAIL))
                .andExpect(jsonPath("roles").isArray())
                .andExpect(jsonPath("roles[0]").value("OFFICER"));
    }

    @Test
    public void testCreateOfficerIfPasswordIsTooShort() throws Exception {
        String email = "user1@example.com";
        String password = "pwd";

        CreateOfficerParams params = new CreateOfficerParams();
        params.setEmail(email);
        params.setPassword(password);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].field_name").value("password"));

        verify(service, never()).createOfficer(email, password);
    }
}