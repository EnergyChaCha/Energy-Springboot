package com.chacha.energy.domain.admin.entity;

import com.chacha.energy.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    @Column(length = 100)
    private String adminId;

    @Column(length = 100)
    private String adminPw;

    @Column(length = 100)
    private String name;

    @Builder
    public Admin(String adminId, String adminPw, String name) {
        this.adminId = adminId;
        this.adminPw = adminPw;
        this.name = name;
    }
}