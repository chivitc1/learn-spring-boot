package com.example.demo.report.web.dto;

import com.example.demo.report.web.validation.ValidCreateReportParams;
import com.example.demo.report.web.validation.ValidReportDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.ZonedDateTime;

@ValidCreateReportParams
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportParams implements Serializable {

    private static final long serialVersionUID = -4404418942821820134L;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateTime;

    @ValidReportDescription
    private String description;

    private boolean trafficIncident;
    private int numberOfInvoledCars;

    private MultipartFile file;
}
