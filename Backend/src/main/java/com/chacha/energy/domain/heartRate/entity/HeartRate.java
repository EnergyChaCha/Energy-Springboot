package com.chacha.energy.domain.heartRate.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "heart_rate")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartRate extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    @Comment("심박수 생성 회원")
    private Member member;
    @Column(name = "bpm")
    private int bpm;

    @Column(name = "exceeds_threshold")
    private Boolean exceedsThreshold;

    @Column(name = "heart_status")
    private int heartStatus;
}
