package com.chacha.energy.domain.member.entity;

import com.chacha.energy.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Column(length = 20)
    private String role;

    @Column(length = 255)
    private String name;

    @Column(length = 12)
    private String loginId;

    @Column(length = 30)
    private String phone;

    @Column(length = 255)
    private String password;

    @Column(length = 255)
    private String work_area;

    private Boolean gender;

    @Column(length = 255)
    private String address;

    @Column(length = 255)
    private String department;

    private int min_bpm_threshold;

    private int max_bpm_threshold;

    private LocalDate birthdate;

}
