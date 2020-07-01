package com.example.demo.report.web;

import com.example.demo.infrastructure.security.ApplicationUserDetails;
import com.example.demo.report.ReportService;
import com.example.demo.report.web.dto.CreateReportParams;
import com.example.demo.report.web.dto.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public ReportDto createReport(@AuthenticationPrincipal ApplicationUserDetails userDetails,
                                 @Valid CreateReportParams params) {
        return ReportDto.fromReport(
                service.createReport(
                        userDetails.getUserId(),
                        params.getDateTime(),
                        params.getDescription(),
                        params.getFile()));
    }
}
