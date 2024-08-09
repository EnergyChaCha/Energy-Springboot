package com.chacha.energy.cj;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cj")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CjEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String step;

    private Double distance;

    private int bpm;

}
