package com.chacha.energy.api.auth.controller;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.api.auth.service.AuthService;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import com.chacha.energy.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "AU-01 회원가입", description = "")
    @PostMapping("/signup")
    public ApiResponse<String> signUp(@Valid @RequestBody AuthDto.SignUpRequest authDto){
        return ApiResponse.success(SuccessCode.POST_SUCCESS, authService.signUp(authDto));
    }

<<<<<<< Updated upstream
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
=======
//    @Operation(summary = "AU-02 로그인", description = "")
//    @PostMapping("/signin")
//    public ApiResponse<AuthDto.AuthResponse> login(@RequestBody AuthDto.AuthRequest authDto) {
//        AuthDto.AuthResponse admin = authService.signin(authDto);
//
//        if (admin.getAdminId() != null) {
//            return ApiResponse.success(SuccessCode.POST_SUCCESS, admin);
//        } else {
//            return ApiResponse.error(ErrorCode.ADMIN_NOT_FOUND, admin);
//        }
//    }
//
//    @Operation(summary = "AU-03 아이디 중복 검사", description = "아이디 중복 체크")
//    @PostMapping("/auth/check/id")
//    public ApiResponse<Boolean> checkDuplicateId(@RequestParam String loginId) {
//        boolean isDuplicate = authService.checkDuplicatedId(loginId);
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, isDuplicate);
//    }

>>>>>>> Stashed changes
}
