package com.chacha.energy.domain.reportReceiver.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.alert.entity.Alert;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.report.entity.Report;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;


@Entity
@Table(name = "report_receiver")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportReceiver extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Comment("신고 받은 사람")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @Comment("신고 내용")
    private Report report;

    @Builder
    public ReportReceiver(Member member, Report report) {
        this.member = member;
        this.report = report;
    }
}
