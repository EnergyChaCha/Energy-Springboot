package com.chacha.energy.api.account.service;

import com.chacha.energy.api.account.dto.AccountDto;
import com.chacha.energy.api.account.dto.ResponseAccountDto;
import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.common.util.MaskingUtil;
import com.chacha.energy.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountService {

    private final MemberRepository memberRepository;

    public AccountDto.Grantdto grantAdmin(AccountDto.Grantdto dto) {

        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_ID, dto.getMemberId())
        );
        member.setRole(dto.getRole());
        memberRepository.save(member);

        return AccountDto.Grantdto.builder().memberId(member.getId()).role(member.getRole()).build();
    }

    public List<ResponseAccountDto> getAllMember() {
        List<Member> members = memberRepository.findAll();

        List<ResponseAccountDto> response = new ArrayList<>();

        for (Member member : members) {
            response.add(
                    ResponseAccountDto.builder()
                            .id(member.getId())
                            .name(MaskingUtil.maskName(member.getName()))
                            .birthdate(member.getBirthdate())
                            .gender(member.getGender())
                            .phone(MaskingUtil.maskPhone(member.getPhone()))
                            .loginId(MaskingUtil.maskLoginId(member.getLoginId()))
                            .workArea(member.getWorkArea())
                            .department(member.getDepartment())
                            .role(member.getRole())
                            .build());
        }

        return response;
    }

    public ResponseAccountDto getMemberDetail(int memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_ID, memberId)
        );
        ResponseAccountDto response = ResponseAccountDto.builder()
                .id(member.getId())
                .name(member.getName())
                .birthdate(member.getBirthdate())
                .gender(member.getGender())
                .phone(member.getPhone())
                .loginId(member.getLoginId())
                .workArea(member.getWorkArea())
                .department(member.getDepartment())
                .role(member.getRole())
                .build();

        return response;
    }


}
