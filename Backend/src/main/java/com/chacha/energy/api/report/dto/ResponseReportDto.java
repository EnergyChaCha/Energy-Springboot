package com.chacha.energy.api.report.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReportDto {
    private int id;
    private int reporterId;
    private int patientId;
    private int confirmerId;
    private String status;
    private int heartRate;
    private double latitude;
    private double longitude;
}
