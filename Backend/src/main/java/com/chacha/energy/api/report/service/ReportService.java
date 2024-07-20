package com.chacha.energy.api.report.service;

import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.api.report.dto.ReportDto;
import com.chacha.energy.api.report.dto.ResponseReportDto;
import com.chacha.energy.api.report.repository.ReportRepository;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.report.entity.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;

    public LocalDateTime convertStringToTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, formatter).atStartOfDay();
    }
    public Page<ResponseReportDto> getAllReportList(ReportDto.RequestAllReports getAllDto) {
        PageRequest pageRequest = PageRequest.of(getAllDto.getPage(), getAllDto.getSize());

        LocalDateTime startTime = convertStringToTime(getAllDto.getStart());
        LocalDateTime endTime = convertStringToTime(getAllDto.getEnd());
        return reportRepository.findAllByTime(startTime, endTime, pageRequest);
    }

    public Report getReportById(int reportId) {
        return reportRepository.findById(reportId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_ID, reportId)
        );
    }

    public ResponseReportDto getReportDto(Report report) {
        return ResponseReportDto.builder()
                .id(report.getId())
                .reporterId(report.getReporter().getId())
                .patientId(report.getPatient().getId())
                .confirmerId(report.getConfirmer() != null ? report.getConfirmer().getId() : 0)
                .heartRate(report.getBpm())
                .latitude(report.getLatitude())
                .longitude(report.getLongitude())
                .status(report.getStatus())
                .build();
    }

    public ResponseReportDto getReportInfo(int reportId) {
        Report report = getReportById(reportId);

        return getReportDto(report);
    }

    public Page<ResponseReportDto> getMyReportList(ReportDto.RequestMyReportList getReportDto) {
        System.out.println("Hi :" + getReportDto.getSize());
        PageRequest pageRequest = PageRequest.of(getReportDto.getPage(), getReportDto.getSize());

        LocalDateTime startTime = convertStringToTime(getReportDto.getStart());
        LocalDateTime endTime = convertStringToTime(getReportDto.getEnd());

        System.out.println(startTime + " " + endTime);

        /* TO-BE : JWT에서 ID를 가져올 수 있도록 수정해야 함 */
        return reportRepository.findMyReportList(startTime, endTime, getReportDto.getId(), pageRequest);
    }

    public ResponseReportDto report(ReportDto.RequestReport reportDto) {
        /* TO-BE : JWT에서 ID를 가져올 수 있도록 수정해야 함 */
        Member reporter = memberRepository.findById(reportDto.getReporterId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_ID, reportDto.getReporterId())
        );

        Member patient = memberRepository.findById(reportDto.getPatientId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_ID, reportDto.getPatientId())
        );

        Report report = new Report(
                reporter, patient, null, reportDto.getHeartRate(),
                reportDto.getLatitude(), reportDto.getLongitude(),
                reportDto.getStatus()
        );

        reportRepository.save(report);

        return getReportDto(report);
    }

    public ResponseReportDto confirm(ReportDto.RequestConfirm confirmDto) {
        /* TO-BE : JWT에서 ID를 가져올 수 있도록 수정해야 함 */
        Report report = getReportById(confirmDto.getReportId());
        Member member = memberRepository.findById(confirmDto.getConfirmerId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_ID, confirmDto.getConfirmerId())
        );
        report.setConfirmer(member);
        reportRepository.save(report);
        return getReportDto(report);
    }

    public List<ReportDto.ResponseSearchResult> search(ReportDto.RequestSearchOther searchDto) {
        System.out.println(searchDto);
        List<Member> members = null;
        if(searchDto.getWorkArea() == null || searchDto.getWorkArea().isEmpty()) {
             members = memberRepository.findAllByName(searchDto.getName());
        }
        else {
            members = memberRepository.findAllByNameAndWorkArea(searchDto.getName(), searchDto.getWorkArea());
        }

        if(members != null) {
            List<ReportDto.ResponseSearchResult> responseSearchResults = new ArrayList<>();

            for(Member member : members) {
                responseSearchResults.add(
                        ReportDto.ResponseSearchResult.builder().
                                id(member.getId())
                                .name(member.getName())
                                .phone(member.getPhone())
                                .loginId(member.getLoginId())
                                .gender(member.getGender())
                                .workArea(member.getWorkArea())
                                .department(member.getDepartment())
                                .birthdate(member.getBirthdate())
                                .build()
                );
            }

            return responseSearchResults;
        }
        else {
            return null;
        }


    }

}
