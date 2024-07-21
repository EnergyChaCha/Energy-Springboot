package com.chacha.energy.api.account.dto;

import lombok.*;

public class AccountDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Grantdto {
        private int memberId;
        private String role;
    }
}
