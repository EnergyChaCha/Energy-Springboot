package com.chacha.energy.domain.member.service;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.api.auth.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    public String signUp(AuthDto.SignUpRequest authDto){
        String encodePassword = bCryptPasswordEncoder.encode(authDto.getPassword());
        authDto.setPassword(encodePassword);
        if(memberRepository.findByLoginId(authDto.getLoginId()).isPresent()){
            throw new CustomException(ErrorCode.SIGNUP_FAILED, authDto.getLoginId());
        }

        Member member = memberRepository.toEntity(authDto);
        memberRepository.save(member);
        return member.getLoginId();

    }
}
