package com.chacha.energy.hello;

import com.chacha.energy.common.costants.SuccessCode;
import com.chacha.energy.common.dto.ApiResponse;
import com.chacha.energy.hello.dto.HelloDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello-token")
public class HelloTokenController {

    @GetMapping("/")
    @Operation(summary = "토큰 예제", description = "토큰이 잘 들어오면 정상작동합니다.")
    public ApiResponse<HelloDto.Response> getToken() {
        String returnData = "토큰 잘 들어있어요!";
        HelloDto.Response dto = new HelloDto.Response(returnData);
        return ApiResponse.success(SuccessCode.GET_SUCCESS, dto);
    }
}

