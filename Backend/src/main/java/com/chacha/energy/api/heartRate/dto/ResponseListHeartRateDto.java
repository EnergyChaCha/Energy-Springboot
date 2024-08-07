package com.chacha.energy.api.heartRate.dto;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListHeartRateDto {
    private Integer id; // member id
    private String name;
    private String phone;
    private String loginId;
    private Integer minBpm;
    private Integer maxBpm;
    private Double avgBpm;
    private Integer minThreshold;
    private Integer maxThreshold;
    private Integer heartrateStatus;
}
