package com.chacha.energy.domain.report.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "report")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    @Comment("접수자(신고한 사람)")
    private Member reporter;

    @ManyToOne
    @JoinColumn(name = "patiend_id")
    @Comment("환자")
    private Member patient;

    @Setter
    @ManyToOne
    @JoinColumn(name = "confirmer_id")
    @Comment("확인한 사람(관리자)")
    private Member confirmer;

    private Integer bpm;

    private Double latitude;

    private Double longitude;

    private String status;

}
