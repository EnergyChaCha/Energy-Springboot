package com.chacha.energy.api.heartRate.dto;

import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.member.entity.Member;
import lombok.*;
import java.time.LocalDate;
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
}
