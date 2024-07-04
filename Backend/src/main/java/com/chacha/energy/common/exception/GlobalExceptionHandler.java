package com.chacha.energy.common.exception;

import com.chacha.energy.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final StringBuilder sb = new StringBuilder();

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<String>> handleCustomException(CustomException customException) {
        log.error("Custom Exception 발생!", customException);

        String stringData =
                customException.getData() == null ? null : customException.getData().toString();
        ApiResponse<String> response = ApiResponse.error(customException.getErrorCode(),
                stringData);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ApiResponse<String>> handleAllExceptions(Exception ex) {
        log.error("Exception 발생!", ex);

        ApiResponse<String> response = ApiResponse.globalError(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage());

        return ResponseEntity.badRequest().body(response);
    }
}

