package com.chacha.energy.api.watch.service;

import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.api.watch.dto.WatchDto;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.member.entity.Role;
import com.chacha.energy.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class WatchService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;


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
}
