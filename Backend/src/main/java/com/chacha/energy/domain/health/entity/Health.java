package com.chacha.energy.domain.health.entity;

import com.chacha.energy.common.entity.BaseEntity;
import com.chacha.energy.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "health")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Health extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "emergency_contact", length = 30)
    private String emergencyContact;

    @Column(name = "emergency_contact_relation", length = 30)
    private String emergencyContactRelation;

    @Column(name = "underlying_conditions")
    private String underlyingConditions;

    @Column(name = "disease")
    private String disease;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "medications")
    private String medications;

    @Column(name = "blood_type", length = 30)
    private String bloodType;

    @Column(name = "organ_donor")
    private Boolean organDonor;

}
