package com.chacha.energy.domain.report.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "접수자(신고한 사람)")
    private Member reporter;

    @ManyToOne
    @JoinColumn(name = "환자")
    private Member patient;

    @Setter
    @ManyToOne
    @JoinColumn(name = "확인한 사람(관리자)")
    private Member confirmer;

    private Integer heartRate;

    private Double latitude;

    private Double longitude;

    private Integer status;

}
