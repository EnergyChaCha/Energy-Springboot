package com.chacha.energy.api.account.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAccountDto {
    private int id;
    private String name;
    private LocalDate birthdate;
    private boolean gender;
    private String phone;
    private String loginId;
    private String workArea;
    private String department;
    private String role;
}

