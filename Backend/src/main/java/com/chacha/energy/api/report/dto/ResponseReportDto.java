package com.chacha.energy.api.report.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReportDto {
    private Integer reportId;
    private Integer reporterId;
    private Integer patientId;
    private Integer confirmerId;
    private String status;
    private Integer bpm;
    private Double latitude;
    private Double longitude;
}
