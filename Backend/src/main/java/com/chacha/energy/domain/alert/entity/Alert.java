package com.chacha.energy.domain.alert.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "alert")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alert extends BaseEntity {
    @Comment("임계치 초과한 사람")
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Comment("심박수")
    private Integer bpm;

    @Comment("위도")
    private Double latitude;

    @Comment("경도")
    private Double longitude;

    @Comment("주소")
    private String address;
}
