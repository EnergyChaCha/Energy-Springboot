package com.chacha.energy.common.dto;

import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.costants.SuccessCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {

    private final Integer status;
    private final String message;
    private T result;

    private ApiResponse(int status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    private ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(SuccessCode code, T result) {
        return new ApiResponse<>(code.getStatus().value(), code.getMessage(), result);
    }

    public static <T> ApiResponse<T> error(ErrorCode code, T result) {
        return new ApiResponse<>(code.getStatus().value(), code.getMessage(), result);
    }

    public static ApiResponse<String> globalError(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), message);
    }
}

