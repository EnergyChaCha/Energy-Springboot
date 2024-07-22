package com.chacha.energy.api.heartRate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHeartRateAvg {
    private int minBpmThreshold;
    private int maxBpmThreshold;
    private double averageThreshold;
}
