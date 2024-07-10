package com.chacha.energy.domain.report.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDateTime;

@Entity
@Table(name = "heart_rate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "접수자(신고한 사람)")
    private Member reporter;

    @ManyToOne
    @JoinColumn(name = "환자")
    private Member patient;

    @ManyToOne
    @JoinColumn(name = "확인한 사람(관리자)")
    private Member confirmer;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Column(name = "심박수")
    private Integer heartRate;

    @Column(name = "위도")
    private Double latitude;

    @Column(name = "경도")
    private Double longitude;

    private String address;

    private String status;

}
