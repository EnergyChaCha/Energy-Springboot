package com.chacha.energy.cj;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cj")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CjEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int step;

    private double distance;

    private Integer originBpm;

    private String bpm;
}
