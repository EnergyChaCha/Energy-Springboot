package com.chacha.energy.api.heartRate.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class HeartRateDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateHeartRateThresholdRequest{
        private int minTreshold;
        private int maxTreshold;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateHeartRateThresholdResponse{
        private int minTreshold;
        private int maxTreshold;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetDetailMemberHeartrateResponse{
        private String name;
        private LocalDate birthdate;
        private Boolean gender;
        private int status;
        private String phone;
        private String loginId;
        private String workArea;
        private String department;

    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetHeartRateAvg{
        private int minBpmThreshold;
        private int maxBpmThreshold;
        private double averageThreshold;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetHeartRateListDto {
        private String id;
        private String name;
        private String phone;
        private String loginId;
        private int minBpm;
        private int maxBpm;
        private int averageBpm;
        private int minThreshold;
        private int maxThreshold;
        private String heartrateStatus;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostHearRateRequest {
        private int bpm;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostHearRateResponse {
        private int bpm;
        private boolean overThreshold;
    }

}
