package com.chacha.energy.domain.heartRate.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "heart_rate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartRate extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "심박수 생성 회원")
    private Member member;
    @Column(name = "심박수")
    private int heartRate;

    @Column(name = "임계치 초과했는지")
    private Boolean exceedsThreshold;
}
