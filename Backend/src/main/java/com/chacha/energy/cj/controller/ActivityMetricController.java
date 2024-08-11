package com.chacha.energy.cj.controller;


import com.chacha.energy.cj.dto.ActivityMetricDto;
import com.chacha.energy.cj.service.ActivityMetricService;
import com.chacha.energy.cj.dto.StepCountRequestDto;
import com.chacha.energy.cj.service.StepCountService;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cj")
public class ActivityMetricController {
    private final ActivityMetricService activityMetricService;
    private final StepCountService stepCountService;

    @Operation(summary = "CJ-00 테스트", description = "잘 들어오는거냐?")
    @GetMapping("/")
    public String test(){
        return "몰라! 되는거냐?";
   }

    @Operation(summary = "CJ-01 근무자명 검색 및 필터 조건", description = "정렬은 order=step-asc,bpm-desc,distance-asc 와 같은 형식으로 사용. 입력한 순서대로 정렬 적용.")
    @GetMapping("/searchName")
    public ApiResponse<Page<ActivityMetricDto.staffListDtoResponse>> getSearchName(@RequestParam(required = false) String name,
                                                                                   @RequestParam Integer page,
                                                                                   @RequestParam Integer size,
                                                                                   @RequestParam(required = false)Integer bpm,
                                                                                   @RequestParam(required = false)Integer step,
                                                                                   @RequestParam(required = false)Double distance,
                                                                                   @RequestParam(required = false)String order
    ) {
        Page<ActivityMetricDto.staffListDtoResponse> result = activityMetricService.searchWorkersByName(name, bpm, step, distance, page, size, order == null ? "": order);
        return ApiResponse.success(SuccessCode.GET_SUCCESS, result);
    }

    @Operation(summary = "CJ-02 심박수 저장 DB", description = "5초에 한번 수집 및 조회")
    @PostMapping("/saveBpm")
    public ApiResponse<ActivityMetricDto.staffListDtoResponse> saveBpm(@RequestBody ActivityMetricDto.staffBpmSaveRequest staffBpmSaveRequest) {
        ActivityMetricDto.staffListDtoResponse result = activityMetricService.saveBpm(staffBpmSaveRequest);
        return ApiResponse.success(SuccessCode.GET_SUCCESS, result);
    }

    @Operation(summary = "CJ-03 watch 걸음수 저장", description = "")
    @PostMapping("/stepcount")
    public ApiResponse<String> saveStepCount(@Valid @RequestBody StepCountRequestDto dto){
        return ApiResponse.success(SuccessCode.POST_SUCCESS, stepCountService.postStepCount(dto));
    }

    @Operation(summary = "CJ-04 심박수 데이터 양방향 암호화", description = "")
    @PostMapping("/encode-bpm")
    public ApiResponse<String> encodeBpm() {
        String result = activityMetricService.encodeBpm();
        return ApiResponse.success(SuccessCode.POST_SUCCESS, result);
    }


}
