package com.chacha.energy.cj.watch;

import com.chacha.energy.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CjWatchEntity extends BaseEntity {
    @Column(name = "step_count")
    private Integer stepCount;
}
