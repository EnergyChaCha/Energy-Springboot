package com.chacha.energy.api.heartRate.dto;

public interface ResponseHeartRateDto {
    String getdate();
    Integer getminimumBpm();
    Integer getmaximumBpm();
    Double getaverageBpm();
}