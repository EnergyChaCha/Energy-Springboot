package com.chacha.energy.api.auth.controller;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.api.auth.service.AuthService;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "AU-01 회원가입", description = "")
    @PostMapping("/signup")
    public ApiResponse<String> regist(@Valid @RequestBody AuthDto.Post authDto) {
        return ApiResponse.success(SuccessCode.POST_SUCCESS, authService.signup(authDto));
    }

    @Operation(summary = "AU-02 로그인", description = "")
    @PostMapping("/signin")
    public ApiResponse<AuthDto.AuthResponse> login(@RequestBody AuthDto.AuthRequest authDto) {
        AuthDto.AuthResponse admin = authService.signin(authDto);

        if (admin.getAdminId() != null) {
            return ApiResponse.success(SuccessCode.POST_SUCCESS, admin);
        } else {
            return ApiResponse.error(ErrorCode.ADMIN_NOT_FOUND, admin);
        }
    }

}
