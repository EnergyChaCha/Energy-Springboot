package com.chacha.energy.api.report.controller;

import com.chacha.energy.api.auth.service.AuthService;
import com.chacha.energy.api.report.dto.ReportDto;
import com.chacha.energy.api.report.dto.ResponseReportDto;
import com.chacha.energy.api.report.service.ReportService;
import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.report.entity.Report;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    private final AuthService authService;

    @Operation(summary = "RI-01 회원별 신고 이력 전체 조회", description = "")
    @GetMapping("/all")
    public ApiResponse<Page<ResponseReportDto>> getAll(ReportDto.RequestAllReports getAllDto){
        // TO-BE : Authentication 에서 Member를 가져와서 ROLE을 비교해야 함.
        return ApiResponse.success(SuccessCode.GET_SUCCESS, reportService.getAllReportList(getAllDto));
    }

    @Operation(summary = "RI-05 신고 이력 상세 조회", description = "")
    @GetMapping(value="/", params="reportId")
    public ApiResponse<ResponseReportDto> getReportDetail(@Valid @RequestParam(name="reportId") int reportId){
        return ApiResponse.success(SuccessCode.GET_SUCCESS, reportService.getReportInfo(reportId));
    }

    @Operation(summary = "RI-07 본인 신고 이력 조회", description = "")
    @GetMapping("/my-report")
    public ApiResponse<Page<ResponseReportDto>> getMyReportList(ReportDto.RequestMyReportList getReportDto){
        System.out.println("MYREPORT : " + getReportDto.getSize() + " " + getReportDto.getId() + " " + getReportDto.getStart()
                + " " +  getReportDto.getEnd());
        return ApiResponse.success(SuccessCode.GET_SUCCESS, reportService.getMyReportList(getReportDto));
    }
    @Operation(summary = "RI-08 신고 확인", description = "")
    @PostMapping("/check")
    public ApiResponse<ResponseReportDto> checkReport(@Valid @RequestBody ReportDto.RequestConfirm confirmDto){
        return ApiResponse.success(SuccessCode.POST_SUCCESS, reportService.confirm(confirmDto));
    }

    @Operation(summary = "RE-01 신고", description = "")
    @PostMapping("/")
    public ApiResponse<ResponseReportDto> report(@Valid @RequestBody ReportDto.RequestReport reportDto){
        return ApiResponse.success(SuccessCode.POST_SUCCESS, reportService.report(reportDto));
    }

    @Operation(summary = "RE-02 이름 근무지 검색", description = "")
    @GetMapping("/other/search")
    public ApiResponse<List<ReportDto.ResponseSearchResult>> searchWorkerByNameAndArea(ReportDto.RequestSearchOther searchDto){
        System.out.println("Hello : " + searchDto.getName() + " " + searchDto.getWorkArea());
        return ApiResponse.success(SuccessCode.GET_SUCCESS, reportService.search(searchDto));
    }

    @Operation(summary = "RE-04 나의 정보", description = "")
    @GetMapping("/my-info")
    public ApiResponse<ReportDto.ResponseMyInfo> getMyInfo(@RequestParam(name="memberId") int memberId){
        /* TO-BE : JWT에서 ID를 가져올 수 있도록 수정해야 함 */
        Member member = authService.getMemberById(memberId);

        return ApiResponse.success(SuccessCode.GET_SUCCESS, ReportDto.ResponseMyInfo.builder()
                .id(member.getId())
                .name(member.getName())
                .phone(member.getPhone())
                .workArea(member.getWorkArea())
                .department(member.getDepartment())
                .build());
    }
}