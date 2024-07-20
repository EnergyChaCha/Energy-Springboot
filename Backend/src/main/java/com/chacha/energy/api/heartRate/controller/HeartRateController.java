package com.chacha.energy.api.heartRate.controller;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.api.heartRate.dto.HeartRateDto;
import com.chacha.energy.api.heartRate.service.HeartService;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/heartrate")
public class HeartRateController {
    private final HeartService heartService;

    @Operation(summary = "HI-01 회원별 심박수 임계치 설정", description = "")
    @PutMapping("/threshold/{memberId}")
    public ApiResponse<String> updateHeartRateTreshold(@Valid @RequestBody HeartRateDto.UpdateHeartRateThresholdRequest heartRateDto,
                                                       @PathVariable("memberId") int id){
        Map<String, Integer> updateTreshold = heartService.updateHeartRateThreshold(id, heartRateDto.getMinTreshold(), heartRateDto.getMaxTreshold());
        return ApiResponse.success(SuccessCode.PATCH_SUCCESS, updateTreshold.toString());
    }

    @Operation(summary = "HI-02 회원별 심박수 임계치", description = "")
    @GetMapping("/threshold/{memberId}")
    public ApiResponse<String> getHeartRateTreshold(@RequestParam int id) {
        Map<String, Integer> threshold = heartService.getHeartRateByMemberId(id);

        return ApiResponse.success(SuccessCode.GET_SUCCESS, threshold.toString());
    }

    @Operation(summary = "HI-03 회원의 심박수 상세조회", description = "관리자가 리스트에서 클릭했을때 해당 회원의 심박수 상세 정보 조회")
    @GetMapping("/detail/{memberId}")
    public ApiResponse<HeartRateDto.GetDetailMemberHeartrateResponse> getDetailListByMemberHeartRate(@RequestParam int id) {
        HeartRateDto.GetDetailMemberHeartrateResponse threshold = heartService.getMemberHeartRateDetail(id);

        return ApiResponse.success(SuccessCode.GET_SUCCESS, threshold);
    }

//    @Operation(summary = "HI-04 일반 사용자 심박수 통계 조회", description = "주어진 기간동안의 평균, 최소, 최대 심박수를 숫자로 보여준다.")
//    @GetMapping("/statistics")
//    public ApiResponse<String> getHeartRateData(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end) {
//
//        HeartRateDto.GetHeartRateAvg threshold = heartService.getHeartRateStatistics(start, end);
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, threshold.toString());
//    }

//    @Operation(summary = "HI-09 심박수 저장", description = "심박수 저장")
//    @PostMapping("/detail/{memberId}")
//    public ApiResponse<String> getHeartRateSave(@RequestParam int id) {
//        Map<String, Integer> threshold = heartService.getHeartRateByMemberId(id);
//
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, threshold.toString());
//    }
}
