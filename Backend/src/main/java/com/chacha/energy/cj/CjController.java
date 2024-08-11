package com.chacha.energy.cj;


import com.chacha.energy.api.report.dto.ReportDto;
import com.chacha.energy.api.report.dto.ResponseAllReportDto;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Operation(summary = "CJ-01 근무자명 검색 및 필터 조건", description = "근무자명 검색")
    @GetMapping("/searchName")
    public ApiResponse<Page<CjDto.staffListDtoResponse>> getSearchName(@RequestParam(required = false) String name,
                                                                       @RequestParam Integer page,
                                                                       @RequestParam Integer size,
                                                                       @RequestParam(required = false)Integer bpm,
                                                                       @RequestParam(required = false)Integer step,
                                                                       @RequestParam(required = false)Double distance,
                                                                       @RequestParam(required = false)String order
    ) {
        Page<CjDto.staffListDtoResponse> result = cjService.searchWorkersByName(name, bpm, step, distance, page, size, order == null ? "": order);
        return ApiResponse.success(SuccessCode.GET_SUCCESS, result);
    }

    @Operation(summary = "CJ-02 심박수 저장 DB", description = "5초에 한번 수집 및 조회")
    @PostMapping("/saveBpm")
    public ApiResponse<CjDto.staffListDtoResponse> saveBpm(@RequestBody CjDto.staffBpmSaveRequest staffBpmSaveRequest) {
        CjDto.staffListDtoResponse result = cjService.saveBpm(staffBpmSaveRequest);
        return ApiResponse.success(SuccessCode.GET_SUCCESS, result);
    }


}
