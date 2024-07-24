package com.chacha.energy.api.watch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class WatchDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyInfo {
        private String name;
        private String loginId;
        private String workArea;
        private String department;
        private String gender;
        private String birthDate;
        private Float minThreshold;
        private Float maxThreshold;
        private Boolean isAdmin;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportRequest {
        private Integer bpm;
        private Double longitude;
        private Double latitude;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReportResponse {
        private Integer reportId;
    }
}
