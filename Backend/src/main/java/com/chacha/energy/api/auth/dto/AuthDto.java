package com.chacha.energy.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthRequest {
        @Setter
        private String adminId;
        private String adminPw;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    public static class AuthResponse {

        private String adminId;
        @Setter
        private String name;
        private String accessToken;
        private String refreshToken;

        @Builder
        public AuthResponse(String adminId, String accessToken, String refreshToken, String name, String branch) {
            this.adminId = adminId;
            this.accessToken = accessToken;
            this.name = name;
            this.refreshToken = refreshToken;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "adminId 내용을 입력해주세요")
        private String adminId;
        @NotBlank(message = "adminPw 내용을 입력해주세요")
        private String adminPw;
        @NotBlank(message = "name 내용을 입력해주세요")
        private String name;
    }

}
