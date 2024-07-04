package com.chacha.energy.api.auth.service;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.api.auth.jwt.TokenProvider;
import com.chacha.energy.api.auth.mapper.AuthMapper;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.domain.admin.entity.Admin;
import com.chacha.energy.domain.admin.repository.AdminRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
    private final AdminRepository adminRepository;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthMapper authMapper;

    public String signup(AuthDto.@Valid Post authDto) {
        String encodePassword = bCryptPasswordEncoder.encode(authDto.getAdminPw());
        authDto.setAdminPw(encodePassword);
        if (adminRepository.findByAdminId(authDto.getAdminId()).isPresent()) {
            throw new CustomException(ErrorCode.SIGNUP_FAILED, authDto.getAdminId());
        }
        adminRepository.save(authMapper.AuthDtoToAdmin(authDto));
        return authDto.getAdminId();
    }

    @Transactional
    public AuthDto.AuthResponse signin(AuthDto.AuthRequest authDto) {
        Optional<Admin> adminOptional = adminRepository.findByAdminId(authDto.getAdminId());

        Admin admin = null;
        if (adminOptional.isPresent()) {
            admin = adminOptional.get();
        }

        if (!(admin != null && bCryptPasswordEncoder.matches(authDto.getAdminPw(), admin.getAdminPw()))) {
            throw new CustomException(ErrorCode.SIGNIN_FAILED, authDto.getAdminId());
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(admin.getAdminId(), admin.getAdminPw());
        // SecurityContextHolder에 로그인 한 유저 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthDto.AuthResponse authResponse = tokenProvider.generateTokenResponse(authentication);

        authResponse.setName(admin.getName());

        return authResponse;
    }

}
