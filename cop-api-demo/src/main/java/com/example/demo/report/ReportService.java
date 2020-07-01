package com.example.demo.report;

import com.example.demo.user.UserId;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

public interface ReportService {
    Report createReport(UserId userId, ZonedDateTime dateTime, String description, MultipartFile file);
}
