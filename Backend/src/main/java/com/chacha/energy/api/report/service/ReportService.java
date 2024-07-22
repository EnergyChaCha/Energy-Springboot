package com.chacha.energy.api.report.service;

import com.chacha.energy.api.auth.repository.HealthRepository;
import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.api.heartRate.repository.HeartRateRepository;
import com.chacha.energy.api.report.constant.ReportFlagInfo;
import com.chacha.energy.api.report.dto.ReportDto;
import com.chacha.energy.api.report.dto.ResponseReportDto;
import com.chacha.energy.api.report.dto.ResponseReportFlagInfoDto;
import com.chacha.energy.api.report.repository.ReportReceiverRepository;
import com.chacha.energy.api.report.repository.ReportRepository;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.domain.health.entity.Health;
import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.member.entity.Role;
import com.chacha.energy.domain.member.service.MemberService;
import com.chacha.energy.domain.report.entity.Report;
import com.chacha.energy.domain.reportReceiver.entity.ReportReceiver;
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
import java.util.Optional;

import static com.chacha.energy.api.report.constant.ReportFlagInfo.SEND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final HeartRateRepository heartRateRepository;
    private final ReportReceiverRepository reportReceiverRepository;
    private final HealthRepository healthRepository;

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
                .reportId(report.getId())
                .reporterId(report.getReporter().getId())
                .patientId(report.getPatient().getId())
                .confirmerId(report.getConfirmer() != null ? report.getConfirmer().getId() : 0)
                .bpm(report.getBpm())
                .latitude(report.getLatitude())
                .longitude(report.getLongitude())
                .status(report.getStatus())
                .build();
    }

    public ReportDto.ReportDetailDto getReportInfo(int reportId) {
        Report report = getReportById(reportId);
        Health health = healthRepository.findByMemberId(report.getPatient().getId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_ID, reportId)
        );

        ReportDto.HealthInfoDto healthInfoDto = ReportDto.HealthInfoDto.builder()
                .allergy(health.getAllergies())
                .emergencyNumber(health.getEmergencyContact())
                .emergencyRelationship(health.getEmergencyContactRelation())
                .blood(health.getBloodType())
                .medication(health.getMedications())
                .organDonor(health.getOrganDonor())
                .disease(health.getDisease())
                .bpm(report.getBpm())
                .build();
        ReportDto.PatientDto patientDto = ReportDto.PatientDto.builder()
                .id(report.getPatient().getId())
                .loginId((report.getPatient().getLoginId()))
                .name(report.getPatient().getName())
                .gender(report.getPatient().getGender())
                .phone(report.getPatient().getPhone())
                .workArea(report.getPatient().getWorkArea())
                .department(report.getPatient().getDepartment())
                .latitude(report.getLatitude())
                .longitude(report.getLongitude())
                .status(report.getStatus())
                .address(report.getPatient().getAddress())
                .healthInfoDto(healthInfoDto)
        .build();
        ReportDto.ResponseSearchResult reportDto = ReportDto.ResponseSearchResult.builder()
                .id(report.getReporter().getId())
                .name(report.getReporter().getName())
                .phone(report.getReporter().getPhone())
                .loginId(report.getReporter().getLoginId())
                .gender(report.getReporter().getGender())
                .workArea(report.getReporter().getWorkArea())
                .department(report.getReporter().getDepartment())
                .birthdate(report.getReporter().getBirthdate())
                .build();

        return ReportDto.ReportDetailDto.builder()
                .reporter(reportDto)
                .createdTime(report.getCreatedTime())
                .id(report.getId())
                .patient(patientDto)
        .build();
    }

    public ReportDto.ResponseMyReportList getMyReportList(ReportDto.RequestMyReportList getReportDto) {
        Member member = memberService.getLoginMember();

        System.out.println("Hi :" + getReportDto.getSize());
        PageRequest pageRequest = PageRequest.of(getReportDto.getPage(), getReportDto.getSize());

        LocalDateTime startTime = convertStringToTime(getReportDto.getStart());
        LocalDateTime endTime = convertStringToTime(getReportDto.getEnd());

        System.out.println(startTime + " " + endTime);

        // TODO: 페이지네이션
        Page<ResponseReportFlagInfoDto> dtos = reportRepository.findEveryMyReportList(startTime, endTime, member.getId(), pageRequest);



        ReportDto.ResponseMyReportList result = new ReportDto.ResponseMyReportList();


        for (ResponseReportFlagInfoDto dto: dtos.getContent()) {
            int dtoFlag = ReportFlagInfo.ALL.code;
            if (dto.getPatientId().equals(dto.getReporterId()) && member.getId().equals(dto.getPatientId())) {
                dtoFlag = ReportFlagInfo.SELF.code;
            }
            else if (member.getId().equals(dto.getReporterId())) {
                dtoFlag = ReportFlagInfo.SEND.code;
            }
            else if (member.getId().equals(dto.getPatientId())) {
                dtoFlag = ReportFlagInfo.RECEIVE.code;;
            } else {
                log.error("getMyReportList dtoFlag 분기처리 에러");
            }


            // 본인 신고
            if (dtoFlag == ReportFlagInfo.SELF.code && (getReportDto.getFlag() == ReportFlagInfo.SELF.code || getReportDto.getFlag() == ReportFlagInfo.ALL.code) ) {
                ReportDto.ResponseFlagInfo flagInfo = new ReportDto.ResponseFlagInfo();
                ReportDto.ResponseMyReportItem reportItem = new ReportDto.ResponseMyReportItem(dto, ReportFlagInfo.SELF.name, flagInfo);
                result.getReport().add(reportItem);
            } else if (dtoFlag == ReportFlagInfo.SEND.code && (getReportDto.getFlag() == ReportFlagInfo.SEND.code || getReportDto.getFlag() == ReportFlagInfo.ALL.code) ) {
                // 보낸 신고
                ReportDto.ResponseFlagInfo flagInfo = ReportDto.ResponseFlagInfo.builder()
                        .name(dto.getPatientName())
                        .gender(dto.getPatientGender())
                        .workArea(dto.getPatientWorkArea())
                        .department(dto.getPatientDepartment())
                        .status(dto.getPatientStatus())
                        .build();
                ReportDto.ResponseMyReportItem reportItem = new ReportDto.ResponseMyReportItem(dto, ReportFlagInfo.SEND.name, flagInfo);
                result.getReport().add(reportItem);
            }
            else if (dtoFlag == ReportFlagInfo.RECEIVE.code && (getReportDto.getFlag() == ReportFlagInfo.RECEIVE.code || getReportDto.getFlag() == ReportFlagInfo.ALL.code) ) {
                // 받은 신고
                ReportDto.ResponseFlagInfo flagInfo = new ReportDto.ResponseFlagInfo();
                ReportDto.ResponseMyReportItem reportItem = new ReportDto.ResponseMyReportItem(dto, ReportFlagInfo.RECEIVE.name, flagInfo);
                result.getReport().add(reportItem);
            } else {
                log.error("getMyReportList 데이터 넣기 분기처리 에러");
            }
        }

        return result;

    }

    public ResponseReportDto report(ReportDto.RequestReport reportDto) {
        Member reporter = memberService.getLoginMember();

        Member patient = memberRepository.findById(reportDto.getPatientId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_ID, reportDto.getPatientId())
        );

        Optional<Integer> heartRate = heartRateRepository.findLatestBpmWithin5Minutes(patient.getId());
        // TODO: 신고 상황에서 워치를 안 쓰고 있던 환자가 있을 수도 있다
        int bpm = -1;
        if (heartRate.isPresent()) {
            bpm = heartRate.get();
        }

        Report report = new Report(
                reporter, patient, null, bpm,
                reportDto.getLatitude(), reportDto.getLongitude(),
                reportDto.getStatus()
        );
        report = reportRepository.save(report);

        List<Member> admins = memberRepository.findAllByRole(Role.ADMIN.name());

        for(Member admin: admins) {
            ReportReceiver reportReceiver= ReportReceiver.builder().report(report).member(admin).build();
            reportReceiverRepository.save(reportReceiver);
        }

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
