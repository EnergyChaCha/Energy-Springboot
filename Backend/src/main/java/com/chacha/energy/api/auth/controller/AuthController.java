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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "AU-01 회원가입", description = "")
    @PostMapping("/signup")
    public ApiResponse<Map<String, Integer>> signUp(@Valid @RequestBody AuthDto.SignUpRequest authDto){
        Map<String, Integer> response = new HashMap<>();
        response.put("id", authService.signUp(authDto));
        return ApiResponse.success(SuccessCode.POST_SUCCESS, response);
    }

    @Operation(summary = "AU-03 아이디 중복 검사", description = "아이디 중복 체크")
    @GetMapping("/check/id")
    public ApiResponse<Boolean> checkDuplicateId(@RequestParam String loginId) {
        boolean isDuplicate = authService.isDuplicatedLoginId(loginId);
        return ApiResponse.success(SuccessCode.GET_SUCCESS, isDuplicate);
    }

    @Operation(summary = "AU-04 건강정보 등록", description = "")
    @PostMapping("/health-info/{memberId}")
    public ApiResponse<Integer> createHealthInfo(@Valid @RequestBody AuthDto.RegisterHealthInfoRequest authDto,
                                                 @PathVariable("memberId") int memberId){
        return ApiResponse.success(SuccessCode.POST_SUCCESS, authService.registerHealthInfo(authDto, memberId));
    }

    @Operation(summary = "AU-05 로그인", description = "")
    @PostMapping("/signin")
    public ApiResponse<AuthDto.SignInResponse> signIn(@RequestBody AuthDto.SignInRequest authDto) {
        AuthDto.SignInResponse authResponse = authService.signIn(authDto);

        if (authResponse != null) {
            return ApiResponse.success(SuccessCode.POST_SUCCESS, authResponse);
        } else {
            return ApiResponse.error(ErrorCode.SIGNIN_FAILED, authResponse);
        }
    }
}
