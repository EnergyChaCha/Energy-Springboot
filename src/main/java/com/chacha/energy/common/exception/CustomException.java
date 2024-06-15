package com.chacha.energy.common.exception;

import com.chacha.energy.common.costants.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    // exception의 원인이 된 값
    private Object data;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    @Builder
    public CustomException(ErrorCode errorCode, Object data) {
        this(errorCode);
        this.data = data;
    }
}
