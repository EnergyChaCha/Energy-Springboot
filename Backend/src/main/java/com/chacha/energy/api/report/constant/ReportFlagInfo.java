package com.chacha.energy.api.report.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReportFlagInfo {
    ALL(0, "전체"),
    SELF(1, "본인 신고"),
    SEND(2, "보낸 신고"),
    RECEIVE(3, "받은 신고");

    public int code;
    public String name;
}
