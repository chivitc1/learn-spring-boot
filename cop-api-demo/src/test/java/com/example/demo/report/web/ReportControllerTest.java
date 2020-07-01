package com.example.demo.report.web;

import com.example.demo.infrastructure.test.CopControllerTest;
import com.example.demo.report.Report;
import com.example.demo.report.ReportId;
import com.example.demo.report.ReportService;
import com.example.demo.report.web.dto.CreateReportParams;
import com.example.demo.user.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.UUID;

import static com.example.demo.infrastructure.security.SecurityHelperForMockMvc.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@CopControllerTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReportService service;

    @Test
    void officerIsAbleToPostAReport() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
        String dateTime = "2018-04-11T22:59:03.189+02:00";
        String description = "This is a test report description about a suspect";
        MockMultipartFile file = createMockFile();


        when(service.createReport(
                eq(Users.officer().getId()),
                any(ZonedDateTime.class),
                eq(description),
                any(MockMultipartFile.class)))
                .thenReturn(
                        new Report(
                                new ReportId(UUID.randomUUID()),
                                Users.officer(),
                                ZonedDateTime.parse(dateTime),
                                description));

        mvc.perform(multipart("/api/reports")
                .file(file)
                .header(HEADER_AUTHORIZATION, bearer(accessToken))
                .param("dateTime", dateTime)
                .param("description", description))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("reporter").value(Users.OFFICER_EMAIL))
                .andExpect(jsonPath("date_time").value("2018-04-11T22:59:03.189+02:00"))
                .andExpect(jsonPath("description").value(description));

    }

    private MockMultipartFile createMockFile() {
        return new MockMultipartFile("file",
                "picture.png",
                "image/png",
                new byte[]{1, 2, 3});
    }

    @Test
    void invalidReportDescription() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
        String dateTime = "2018-04-11T22:59:03.189+02:00";
        String description = "This is a test report description";
        MockMultipartFile file = createMockFile();

        when(service.createReport(
                eq(Users.officer().getId()),
                any(ZonedDateTime.class),
                eq(description),
                any(MockMultipartFile.class)))
                .thenReturn(new Report(new ReportId(UUID.randomUUID()), Users.officer(), ZonedDateTime.parse(dateTime), description));

        mvc.perform(multipart("/api/reports")
                .file(file)
                .header(HEADER_AUTHORIZATION, bearer(accessToken))
                .param("dateTime", dateTime)
                .param("description", description))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("errors").isNotEmpty())
                .andExpect(jsonPath("errors[0].field_name").value("description"));

    }
}