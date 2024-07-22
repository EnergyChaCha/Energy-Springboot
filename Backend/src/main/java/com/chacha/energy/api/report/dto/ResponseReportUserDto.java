package com.chacha.energy.api.report.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReportUserDto {
    private Integer id;
    private String loginId;
    private String name;
    private String phone;
}
