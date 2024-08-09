package com.chacha.energy.cj;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cj")
public class CjController {
    private final CjService cjService;

    @Operation(summary = "CJ-01 테스트", description = "잘 들어오는거냐?")
    @GetMapping("/")
    public String test(){
        return "몰라! 되는거냐?";
   }
}
