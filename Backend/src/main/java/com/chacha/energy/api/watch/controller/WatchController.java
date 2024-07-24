package com.chacha.energy.api.watch.controller;

import com.chacha.energy.api.watch.dto.WatchDto;
import com.chacha.energy.api.watch.service.WatchService;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/watch")
public class WatchController {
    private final WatchService watchService;

    @Operation(summary = "WA-07 본인 정보 조회", description = "")
    @GetMapping("/my-info")
    public ApiResponse<WatchDto.MyInfo> getMyInfo() {
        return ApiResponse.success(SuccessCode.GET_SUCCESS, watchService.getMyInfo());
    }

    @Operation(summary = "WA-02 본인 신고", description = "본인 신고")
    @PostMapping("/report")
    public ApiResponse<WatchDto.ReportResponse> postMyReport(@RequestBody WatchDto.ReportRequest reportRequest) {
        return ApiResponse.success(SuccessCode.POST_SUCCESS, watchService.postMyReport(reportRequest));
    }

    @Operation(summary = "WA-04 심박수 초과 알림 리스트 조회", description = "")
    @GetMapping("/notification/heartrate")
    public ApiResponse<List<WatchDto.NotificationResponse>> getThresholdExceedList() {
        return ApiResponse.success(SuccessCode.GET_SUCCESS, watchService.getThresholdExceedList());
    }

    @Operation(summary = "WA-06 신고 알림 리스트 조회", description = "")
    @GetMapping("/notification/report")
    public ApiResponse<List<WatchDto.NotificationResponse>> getReportList() {
        return ApiResponse.success(SuccessCode.GET_SUCCESS, watchService.getReportList());
    }
}
