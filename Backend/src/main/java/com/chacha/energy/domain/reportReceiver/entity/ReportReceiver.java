package com.chacha.energy.domain.reportReceiver.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.report.entity.Report;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class ReportReceiver extends BaseEntity {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    private Member receiver;

    @ManyToOne
    @JoinColumn(name = "신고 내용")
    private Report report;

}
