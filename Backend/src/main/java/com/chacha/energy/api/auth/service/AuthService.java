package com.chacha.energy.api.auth.service;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.api.auth.jwt.TokenProvider;
import com.chacha.energy.api.auth.mapper.AuthMapper;
import com.chacha.energy.api.auth.repository.HealthRepository;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.domain.health.entity.Health;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.domain.member.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthMapper authMapper;
    private final MemberRepository memberRepository;
    private final HealthRepository healthRepository;

    public Member getMemberById(int id) {
        Member member = memberRepository.findById(id).orElseThrow(
                ()-> new CustomException(ErrorCode.NO_ID, id)
        );
        return member;
    }

    // AU-01 회원가입
    public String signUp(AuthDto.SignUpRequest authDto){
        String encodePassword = bCryptPasswordEncoder.encode(authDto.getPassword());
        authDto.setPassword(encodePassword);
        if(memberRepository.findByLoginId(authDto.getLoginId()).isPresent()){
            throw new CustomException(ErrorCode.SIGNUP_FAILED, authDto.getLoginId());
        }

        Member member = authMapper.toEntity(authDto);
        member.setCreatedTime(LocalDateTime.now());
        member.setUpdatedTime(LocalDateTime.now());
        member.setRole(Role.ADMIN.name());
        if (memberRepository.count() > 0L) {
            member.setRole(Role.USER.name());
        }
        memberRepository.save(member);
        return member.getLoginId();

    }

    // AU-02 아이디 중복확인
    public boolean isDuplicatedLoginId(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }

    // AU-04 건강정보 등록
    public Integer registerHealthInfo(AuthDto.RegisterHealthInfoRequest authDto, int memberId){
        Health health = authMapper.toHealthEntity(authDto);
        health.setMember(memberRepository.findById(memberId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_ID, memberId)
        ));
        healthRepository.save(health);
        return health.getId();
    }

    // AU-05 로그인
    public AuthDto.SignInResponse signIn(AuthDto.SignInRequest authDto){
        Member member = memberRepository.findByLoginId(authDto.getLoginId()).orElseThrow(
                () -> new CustomException(ErrorCode.SIGNIN_FAILED, authDto.getLoginId())
        );

        if (!bCryptPasswordEncoder.matches(authDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.SIGNIN_FAILED);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), member.getPassword());
        // SecurityContextHolder에 로그인 한 유저 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthDto.SignInResponse authResponse = tokenProvider.generateTokenResponse(authentication);

        authResponse.setIsAdmin(false);
        if (member.getRole().equals(Role.ADMIN.name())) {
            authResponse.setIsAdmin(true);
        }

        return authResponse;
    }
}
