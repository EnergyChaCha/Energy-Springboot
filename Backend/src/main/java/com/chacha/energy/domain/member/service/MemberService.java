package com.chacha.energy.domain.member.service;

import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getLoginMember() throws CustomException {
        // 현재 로그인한 유저의 memberId(pk)를 반환
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        if (authentication.equalsIgnoreCase("AnonymousUser")) {
            // 토큰 인증이 필요 없는 uri에서 해당 메서드 호출 할 경우 발생
            // JwtFilter에서 토큰 인증이 필요 없는 uri 목록 확인할 것
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND_WITH_TOKEN);
        }
        return memberRepository.findById(Integer.parseInt(authentication)).orElseThrow(() -> new CustomException(
                ErrorCode.MEMBER_NOT_FOUND, authentication));
    }

    // TODO: 보안 로직 추가
    public static String encryptIntToUUID(Integer memberId) {
        return memberId.toString();
    }

    public static Integer decryptUUIDToInt(String uuid) {
        return Integer.parseInt(uuid);
    }
}
