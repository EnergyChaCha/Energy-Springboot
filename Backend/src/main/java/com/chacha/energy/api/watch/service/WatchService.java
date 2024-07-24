package com.chacha.energy.api.watch.service;

import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.api.report.repository.ReportReceiverRepository;
import com.chacha.energy.api.report.repository.ReportRepository;
import com.chacha.energy.api.watch.dto.WatchDto;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.member.entity.Role;
import com.chacha.energy.domain.member.service.MemberService;
import com.chacha.energy.domain.report.entity.Report;
import com.chacha.energy.domain.reportReceiver.entity.ReportReceiver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class WatchService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ReportReceiverRepository reportReceiverRepository;
    private final ReportRepository reportRepository;


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
}
