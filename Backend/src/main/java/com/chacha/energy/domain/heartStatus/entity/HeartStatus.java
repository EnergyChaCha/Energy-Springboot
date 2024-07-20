package com.chacha.energy.domain.heartStatus.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "heart_status")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class HeartStatus extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(name = "status")
    private int status;
    
}
