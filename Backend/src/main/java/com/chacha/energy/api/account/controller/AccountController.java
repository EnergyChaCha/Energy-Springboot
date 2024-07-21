package com.chacha.energy.api.account.controller;

import com.chacha.energy.api.account.dto.AccountDto;
import com.chacha.energy.api.account.dto.ResponseAccountDto;
import com.chacha.energy.api.account.service.AccountService;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "AM-01 관리자 지정/해제")
    @PostMapping("/grant")
    public ApiResponse<AccountDto.Grantdto> postGrant(@Valid @RequestBody AccountDto.Grantdto dto){
        return ApiResponse.success(SuccessCode.POST_SUCCESS, accountService.grantAdmin(dto));
    }

    @Operation(summary = "AM-02 회원 목록 조회")
    @GetMapping("/member/all")
    public ApiResponse<List<ResponseAccountDto>> getMemberList(){
        return ApiResponse.success(SuccessCode.GET_SUCCESS, accountService.getAllMember());
    }

    @Operation(summary = "AM-03 회원 목록 상세 조회")
    @GetMapping("/member")
    public ApiResponse<ResponseAccountDto> getMemberDetail(@RequestParam(name="memberId") int memberId){
        return ApiResponse.success(SuccessCode.GET_SUCCESS, accountService.getMemberDetail(memberId));
    }

}