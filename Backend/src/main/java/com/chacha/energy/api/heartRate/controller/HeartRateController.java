package com.chacha.energy.api.heartRate.controller;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.api.heartRate.dto.HeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseHeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseListHeartRateDto;
import com.chacha.energy.api.heartRate.service.HeartService;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/heartrate")
public class HeartRateController {
    private final HeartService heartService;

    @Operation(summary = "HT-01 회원별 심박수 임계치 설정", description = "")
    @PutMapping("/threshold/{memberId}")
    public ApiResponse<HeartRateDto.UpdateHeartRateThresholdResponse> updateHeartRateTreshold(@Valid @RequestBody HeartRateDto.UpdateHeartRateThresholdRequest heartRateDto,
                                                       @PathVariable("memberId") int id){
        HeartRateDto.UpdateHeartRateThresholdResponse updateTreshold = heartService.updateHeartRateThreshold(id, heartRateDto.getMinTreshold(), heartRateDto.getMaxTreshold());
        return ApiResponse.success(SuccessCode.PATCH_SUCCESS, updateTreshold);
    }

    @Operation(summary = "HT-02 회원별 심박수 임계치 조회", description = "")
    @GetMapping("/threshold/{memberId}")
    public ApiResponse<HeartRateDto.UpdateHeartRateThresholdResponse> getHeartRateTreshold(@PathVariable("memberId") int id) {
        HeartRateDto.UpdateHeartRateThresholdResponse threshold = heartService.getHeartRateByMemberId(id);

        return ApiResponse.success(SuccessCode.GET_SUCCESS, threshold);
    }


    @Operation(summary = "HI-09 심박수 저장", description = "워치에서 받아온 심박수를 저장하는데 임계치 초과 여부도 저장한다.")
    @PostMapping("/{memberId}")
    public ApiResponse<HeartRateDto.PostHearRateResponse> getHeartRateSave(@Valid @RequestBody HeartRateDto.PostHearRateRequest postHearRateRequest,
                                                                           @PathVariable("memberId") int id) {

        HeartRateDto.PostHearRateResponse hearRateResponse = heartService.saveHeartRate(id, postHearRateRequest.getBpm());
        return ApiResponse.success(SuccessCode.GET_SUCCESS,hearRateResponse);
    }


    @Operation(summary = "HI-01 일반 사용자 심박수 통계 조회", description = "주어진 기간동안의 평균, 최소, 최대 심박수를 숫자로 보여준다.")
    @GetMapping("/{memberId}")
    public ApiResponse<List<ResponseHeartRateDto>> getHeartRateData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam String interval,
            @PathVariable("memberId") int id) {

        List<ResponseHeartRateDto> threshold = heartService.getGraphByHeartRate(id,start.atStartOfDay(),end.atStartOfDay(),interval);
        return ApiResponse.success(SuccessCode.GET_SUCCESS, threshold);
    }

//    @Operation(summary = "HI-04 일반 사용자 심박수 통계 조회", description = "주어진 기간동안의 평균, 최소, 최대 심박수를 숫자로 보여준다.")
//    @GetMapping("/statistics/{memberId}")
//    public ApiResponse<String> getHeartRateData(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end, @PathVariable("memberId") int id) {
//
//        HeartRateDto.GetHeartRateAvg threshold = heartService.getHeartRateStatistics(start, end);
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, threshold.toString());
//    }

    @Operation(summary = "HI-02 일반 사용자 심박수 통계 조회", description = "전체 정보 조회 기능, 아이디별 검색 가능")
    @GetMapping("/all")
    public ApiResponse<List<ResponseListHeartRateDto>> getHeartRateData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(name = "heartrate-status", required = false) String heartrateStatus,
            @RequestParam(required = false) String loginId) {

        List<ResponseListHeartRateDto> heartRates = heartService.getAllHeartRates(start.atStartOfDay(), end.atStartOfDay(), heartrateStatus, loginId);

        return ApiResponse.success(SuccessCode.GET_SUCCESS, heartRates);
    }

//    @Operation(summary = "HI-03 회원 심박수 상세 정보 조회", description = "관리자가 리스트에서 클릭했을때 해당 회원의 심박수 상세 정보 조회")
//    @GetMapping("/statistics")
//    public ApiResponse<String> getHeartRateData(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime start,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime end) {
//
//        HeartRateDto.GetHeartRateAvg threshold = heartService.getHeartRateStatistics(start, end);
//        return ApiResponse.success(SuccessCode.GET_SUCCESS, threshold.toString());
//    }


}
