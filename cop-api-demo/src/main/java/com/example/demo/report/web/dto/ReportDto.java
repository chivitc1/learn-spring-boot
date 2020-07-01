package com.example.demo.report.web.dto;

import com.example.demo.report.Report;
import com.example.demo.report.ReportId;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class ReportDto {
    private ReportId id;
    private String reporter;
    private ZonedDateTime dateTime;
    private String description;

    public static ReportDto fromReport(Report report) {
        return ReportDto.builder()
                .id(report.getId())
                .reporter(report.getReporter().getEmail())
                .dateTime(report.getDateTime())
                .description(report.getDescription())
                .build();
    }
}
