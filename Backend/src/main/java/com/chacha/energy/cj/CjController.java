package com.chacha.energy.cj;


import com.chacha.energy.common.costants.ErrorCode;
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
@RequestMapping("/cj")
public class CjController {
    private final CjService cjService;

    @Operation(summary = "CJ-00 테스트", description = "잘 들어오는거냐?")
    @GetMapping("/")
    public String test(){
        return "몰라! 되는거냐?";
   }

    @Operation(summary = "CJ-01", description = "근무자명 검색")
    @GetMapping("/searchName")
    public ApiResponse<List<CjDto.staffListDtoResponse>> getSearchName(@RequestParam String name) {
        List<CjDto.staffListDtoResponse> result = cjService.searchWorkersByName(name);
        return ApiResponse.success(SuccessCode.GET_SUCCESS, result);
    }

//
//    @Operation(summary = "CJ-02", description = "필터 및 정렬")
//    @GetMapping("/check/id")
//    public ApiResponse<Boolean> checkDuplicateId(@RequestParam String loginId) {
//        boolean isDuplicate = authService.isDuplicatedLoginId(loginId);
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, isDuplicate);
//    }
//
//    @Operation(summary = "CJ-03", description = "필터 조건")
//    @GetMapping("/check/id")
//    public ApiResponse<Boolean> checkDuplicateId(@RequestParam String loginId) {
//        boolean isDuplicate = authService.isDuplicatedLoginId(loginId);
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, isDuplicate);
//    }
//
//    @Operation(summary = "CJ-04", description = "리스트 표시 개수")
//    @GetMapping("/check/id")
//    public ApiResponse<Boolean> checkDuplicateId(@RequestParam String loginId) {
//        boolean isDuplicate = authService.isDuplicatedLoginId(loginId);
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, isDuplicate);
//    }
//
//    @Operation(summary = "CJ-05", description = "페이징 처리")
//    @GetMapping("/check/id")
//    public ApiResponse<Boolean> checkDuplicateId(@RequestParam String loginId) {
//        boolean isDuplicate = authService.isDuplicatedLoginId(loginId);
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, isDuplicate);
//    }
//    @Operation(summary = "CJ-06", description = "데이터")
//    @GetMapping("/check/id")
//    public ApiResponse<Boolean> checkDuplicateId(@RequestParam String loginId) {
//        boolean isDuplicate = authService.isDuplicatedLoginId(loginId);
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, isDuplicate);
//    }

}
