package com.chacha.energy.api.heartRate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHeartRateDto {
    private String date;
    private int minBpm;
    private int maxBpm;
    private double averageBpm;
}
