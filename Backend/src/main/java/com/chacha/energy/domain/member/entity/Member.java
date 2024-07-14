package com.chacha.energy.domain.member.entity;

import com.chacha.energy.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member extends BaseEntity {

    @Column(name = "role", length = 10)
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
    private String workArea;

    private Boolean gender;

    @Column(length = 255)
    private String address;

    @Column(length = 255)
    private String department;

    private int minBpmThreshold;

    private int maxBpmThreshold;

    private LocalDate birthdate;

}
