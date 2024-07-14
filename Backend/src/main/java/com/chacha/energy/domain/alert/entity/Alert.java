package com.chacha.energy.domain.alert.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "alert")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alert extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "임계치 초과한 사람", nullable = false)
    private Member member;

    @Column(name = "심박수")
    private Integer heartRate;

    @Column(name = "위도")
    private Double latitude;

    @Column(name = "경도")
    private Double longitude;

    @Column(name = "주소")
    private String address;
}
