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
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "alert_receiver")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlertReceiver extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Comment("알림 받은 사람")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "alert_id")
    @Comment("임계치 초과 알림 내용")
    private Alert alert;

}
