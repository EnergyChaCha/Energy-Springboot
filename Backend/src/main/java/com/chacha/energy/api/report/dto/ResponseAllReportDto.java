package com.chacha.energy.api.report.dto;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAllReportDto {
    private Integer reportId;
    private Double latitude;
    private Double longitude;
    private Integer bpm;
    private LocalDateTime createdTime;
    private ResponseReportUserDto reporter;
    private ResponseReportUserDto patient;
}
