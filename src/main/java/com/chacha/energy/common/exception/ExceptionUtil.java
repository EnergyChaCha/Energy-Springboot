package com.chacha.energy.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@RequiredArgsConstructor
@Component
public class ExceptionUtil {
    private final StringBuilder sb = new StringBuilder();

    public static String exceptionToString(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}

