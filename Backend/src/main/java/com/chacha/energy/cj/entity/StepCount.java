package com.chacha.energy.cj.entity;

import com.chacha.energy.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cj_watch_entity")
public class StepCount extends BaseEntity {
    @Column(name = "step_count")
    private Integer stepCount;
}
