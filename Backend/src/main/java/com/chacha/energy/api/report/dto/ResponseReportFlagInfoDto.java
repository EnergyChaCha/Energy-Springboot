package com.chacha.energy.api.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ResponseReportFlagInfoDto {
    private Integer reportId;
    private LocalDateTime createdTime;
    private Integer bpm;
    private Double latitude;
    private Double longitude;
    private String patientName;
    private Boolean patientGender;
    private String patientWorkArea;
    private String patientDepartment;
    private String patientStatus;
    private Integer reporterId;
    private Integer patientId;
}
