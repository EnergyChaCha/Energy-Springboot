package com.chacha.energy.api.watch.service;

import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.api.heartRate.repository.AlertReceiverRepository;
import com.chacha.energy.api.heartRate.repository.AlertRepository;
import com.chacha.energy.api.report.repository.ReportReceiverRepository;
import com.chacha.energy.api.report.repository.ReportRepository;
import com.chacha.energy.api.watch.dto.WatchDto;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.common.util.MaskingUtil;
import com.chacha.energy.domain.alert.entity.Alert;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.member.entity.Role;
import com.chacha.energy.domain.member.service.MemberService;
import com.chacha.energy.domain.report.entity.Report;
import com.chacha.energy.domain.reportReceiver.entity.ReportReceiver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class WatchService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ReportReceiverRepository reportReceiverRepository;
    private final ReportRepository reportRepository;
    private final AlertRepository alertRepository;


    public WatchDto.MyInfo getMyInfo() {
        Member member = memberService.getLoginMember();

        WatchDto.MyInfo response = new WatchDto.MyInfo(
                member.getName(),
                member.getLoginId(),
                member.getWorkArea(),
                member.getDepartment(),
                member.getGender().equals(Boolean.FALSE) ? "여성" : "남성",
                member.getBirthdate().toString(),
                (float) member.getMinBpmThreshold(),
                (float) member.getMaxBpmThreshold(),
                member.getRole().equals(Role.ADMIN.name())
        );

        return response;
    }

    @Transactional
    public WatchDto.ReportResponse postMyReport(WatchDto.ReportRequest reportRequest) {
        Member member = memberService.getLoginMember();

        Report report = new Report(member, member, null, reportRequest.getBpm(), reportRequest.getLatitude(), reportRequest.getLongitude(), "본인 신고");
        List<Member> admins = memberRepository.findAllByRole(Role.ADMIN.name());
        reportRepository.save(report);
        for(Member admin: admins) {
            ReportReceiver reportReceiver = new ReportReceiver(admin, report);
            reportReceiverRepository.save(reportReceiver);
        }

        return new WatchDto.ReportResponse(report.getId());
    }

    public List<WatchDto.NotificationResponse> getThresholdExceedList() {
        Member member = memberService.getLoginMember();

        List<Alert> alertList = new ArrayList<>();
        List<WatchDto.NotificationResponse> response = new ArrayList<>();


        // 모든 계정의 초과 알림
        if(member.getRole().equals(Role.ADMIN.name())) {
            alertList = alertRepository.findAllByCreatedTimeBeforeOrderByCreatedTimeDesc(LocalDateTime.now());
        } else {
            alertList = alertRepository.findAllByMemberAndCreatedTimeBeforeOrderByCreatedTimeDesc(member, LocalDateTime.now());
        }

        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        for (Alert alert: alertList) {
            Member targetMember = alert.getMember();
            float bpm = alert.getBpm().floatValue();

            String thresholdExceedMessage = "이내";
            if (alert.getBpm() < targetMember.getMinBpmThreshold()) {
                thresholdExceedMessage = "미만";
            } else if (alert.getBpm() > targetMember.getMaxBpmThreshold()) {
                thresholdExceedMessage = "초과";
            }

            if (thresholdExceedMessage.equals("이내")) continue;

            stringBuilder.append(MaskingUtil.maskName(targetMember.getName()))
                    .append("(")
                    .append(MaskingUtil.maskLoginId(targetMember.getLoginId()))
                    .append(") 님 심박수가 임계치 ")
                    .append(thresholdExceedMessage)
                    .append("입니다.");
            String message = stringBuilder.toString();
            stringBuilder.setLength(0);

            String timestamp = alert.getCreatedTime().format(formatter);

            WatchDto.NotificationResponse notificationItem = new WatchDto.NotificationResponse(bpm, message, timestamp);
            response.add(notificationItem);
        }

        return response;
    }

    public List<WatchDto.NotificationResponse> getReportList() {
        Member member = memberService.getLoginMember();

        List<Report> reportList = new ArrayList<>();
        List<WatchDto.NotificationResponse> response = new ArrayList<>();

        // 관리자만 접근 가능
        if(!member.getRole().equals(Role.ADMIN.name())) {
            throw new CustomException(ErrorCode.ONLY_ADMIN_AVAILABLE, member.getRole());
        }

        reportList = reportRepository.findAllByCreatedTimeBeforeOrderByCreatedTimeDesc(LocalDateTime.now());


        StringBuilder stringBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        for (Report report: reportList) {
            Member targetMember = report.getPatient();
            float bpm = report.getBpm().floatValue();

            String thresholdExceedMessage = "이내";
            if (report.getBpm() < targetMember.getMinBpmThreshold()) {
                thresholdExceedMessage = "미만";
            } else if (report.getBpm() > targetMember.getMaxBpmThreshold()) {
                thresholdExceedMessage = "초과";
            }

            stringBuilder.append(MaskingUtil.maskName(targetMember.getName()))
                    .append("(")
                    .append(MaskingUtil.maskLoginId(targetMember.getLoginId()))
                    .append(") 님 심박수가 임계치 ")
                    .append(thresholdExceedMessage)
                    .append("입니다.");
            String message = stringBuilder.toString();
            stringBuilder.setLength(0);

            String timestamp = report.getCreatedTime().format(formatter);

            WatchDto.NotificationResponse notificationItem = new WatchDto.NotificationResponse(bpm, message, timestamp);
            response.add(notificationItem);
        }

        return response;
    }
}
