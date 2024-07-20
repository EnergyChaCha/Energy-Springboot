package com.chacha.energy.common.costants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST: 잘못된 요청 구문 */
    // 일반
    NO_ID(BAD_REQUEST, "요청하신 정보가 없습니다"),
    ALREADY_DELETED(BAD_REQUEST, "이미 삭제된 값입니다"),
    BAD_PARAMETER(BAD_REQUEST, "요청 파라미터가 잘못되었습니다."),
    BAD_PARAMETER_TYPE(BAD_REQUEST, "지원하지 않는 파라미터 형식입니다."),

    // 토큰
    NO_TOKEN(BAD_REQUEST, "토큰이 존재하지 않습니다."),
    EXPIRED_TOKEN(UNAUTHORIZED, "토큰의 유효 기간이 만료되었습니다."),
    MALFORMED_TOKEN(UNAUTHORIZED, "토큰의 형식이 올바르지 않습니다."),
    INVALID_SIGNATURE_TOKEN(UNAUTHORIZED, "서명이 유효하지 않습니다."),
    UNSUPPORTED_JWT(UNAUTHORIZED, "지원하지 않는 JWT 기능이 사용되었습니다."),

    // Member
    MEMBER_NOT_FOUND(BAD_REQUEST, "어드민이 존재하지 않습니다."),
    SIGNUP_FAILED(BAD_REQUEST, "이미 존재하는 회원인지 확인해주세요"),
    SIGNIN_FAILED(BAD_REQUEST, "아이디와 비밀번호를 확인해주세요."),
    MEMBER_NOT_FOUND_WITH_TOKEN(INTERNAL_SERVER_ERROR, "토큰인증을 하지 않는 곳에서 로그인한 멤버를 찾으려고 했습니다. [백엔드에 알려주세요]"),

    /* 500 INTERNAL_SERVER_ERROR : 서버 오류 */
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류로 인해 응답을 제공할 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
