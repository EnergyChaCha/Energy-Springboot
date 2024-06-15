package com.chacha.energy.domain.admin.service;

import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.domain.admin.entity.Admin;
import com.chacha.energy.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final AdminRepository adminRepository;

    public Admin getLoginAdmin() throws CustomException {
        // 현재 로그인한 유저의 adminId(pk말고 로그인할 때 쓰는 id)를 반환
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        if (authentication.equalsIgnoreCase("AnonymousUser")) {
            // 토큰 인증이 필요 없는 uri에서 해당 메서드 호출 할 경우 발생
            // JwtFilter에서 토큰 인증이 필요 없는 uri 목록 확인할 것
            throw new CustomException(ErrorCode.ADMIN_NOT_FOUND_WITH_TOKEN);
        }
        return adminRepository.findByAdminId(authentication).orElseThrow(() -> new CustomException(
                ErrorCode.ADMIN_NOT_FOUND, authentication));
    }
}
