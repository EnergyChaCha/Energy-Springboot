package com.chacha.energy.domain.alertReceiver.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.alert.entity.Alert;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "alert_receiver")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlertReceiver extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "신고 받은 사람")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "신고자")
    private Member receiver;

    @ManyToOne
    @JoinColumn(name = "임계치 초과 알림 내용")
    private Alert alert;

}
