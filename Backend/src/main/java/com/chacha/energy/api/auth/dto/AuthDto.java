package com.chacha.energy.api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpRequest {
        private String name;
        private String loginId;
        private String phone;
        private String password;
        private String workArea;
        private Boolean gender;
        private String address;
        private String department;
        private int minBpmThreshold;
        private int maxBpmThreshold;
        private LocalDate birthdate;
        private String role;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpResponse {
        private int id;
        private String name;
        private String loginId;
        private String phone;
        private String password;
        private String workArea;
        private Boolean gender;
        private String address;
        private String department;
        private int minBpmThreshold;
        private int maxBpmThreshold;
        private LocalDate birthdate;
        private String role;
        private LocalDateTime createdTime;
        private LocalDateTime updatedTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RegisterHealthInfoRequest{
        private String emergencyContact;
        private String emergencyContactRelation;
        private String underlyingConditions;
        private String allergies;
        private String medications;
        private String bloodType;
        private Boolean organDonor;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RegisterHealthInfoResponse{
        private int id;
        private String emergencyContact;
        private String emergencyContactRelation;
        private String underlyingConditions;
        private String allergies;
        private String medications;
        private String bloodType;
        private Boolean organDonor;
        private LocalDateTime createdTime;
        private LocalDateTime updatedTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignInRequest{
        private String loginId;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignInResponse{
        private String id;
        private String accessToken;
        private String refreshToken;
    }
}
