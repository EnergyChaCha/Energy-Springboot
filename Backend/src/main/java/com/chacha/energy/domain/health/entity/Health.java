package com.chacha.energy.domain.health.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "health")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Health extends BaseEntity {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "건강정보 주인")
    private Member member;

    @Column(name = "응급 연락처", length = 30)
    private String emergencyContact;

    @Column(name = "응급 연락처 관계", length = 30)
    private String emergencyContactRelation;

    @Column(name = "기저질환")
    private String underlyingConditions;

    @Column(name = "알레르기")
    private String allergies;

    @Column(name = "복용 중인 약물")
    private String medications;

    @Column(name = "혈액형", length = 30)
    private String bloodType;

    @Column(name = "장기기증자 여부")
    private Boolean organDonor;

}
