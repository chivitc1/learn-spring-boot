package com.example.demo.report;

import com.example.demo.user.UserId;
import com.example.demo.user.UserService;
import com.example.demo.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;

    @Override
    public Report createReport(UserId reporterId, ZonedDateTime dateTime, String description, MultipartFile file) {
        return reportRepository.save(new Report(
                reportRepository.nextId(),
                userService.getUser(reporterId)
                    .orElseThrow(() -> new UserNotFoundException(reporterId)),
                dateTime,
                description
        ));
    }
}
