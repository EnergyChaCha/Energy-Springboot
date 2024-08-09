package com.chacha.energy.cj.watch;

import com.chacha.energy.api.auth.dto.AuthDto;
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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cj")
public class CjWatchController {
    private final CjWatchService cjWatchService;

    @Operation(summary = "CJ watch 걸음수 저장", description = "")
    @PostMapping("/stepcount")
    public ApiResponse<String> signUp(@Valid @RequestBody CjWatchDto dto){
        return ApiResponse.success(SuccessCode.POST_SUCCESS, cjWatchService.postStepCount(dto));
    }
}
