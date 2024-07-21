package com.chacha.energy.api.heartRate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListHeartRateDto {
    private int id;
    private String name;
    private String phone;
    private String loginId;
    private int bpm;
    private double averageThreshold;
    private int minThreshold;
    private int maxThreshold;
    private String heartrateStatus;
}
