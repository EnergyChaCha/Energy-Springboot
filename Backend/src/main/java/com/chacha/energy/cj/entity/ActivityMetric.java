package com.chacha.energy.cj.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "cj")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ActivityMetric extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int step;

    private double distance;

    private Integer originBpm;

    private String bpm;
}
