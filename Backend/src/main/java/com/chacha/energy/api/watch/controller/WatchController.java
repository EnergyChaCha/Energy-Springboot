package com.chacha.energy.api.watch.controller;

import com.chacha.energy.api.watch.dto.WatchDto;
import com.chacha.energy.api.watch.service.WatchService;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
