package com.chacha.energy.cj;

import com.chacha.energy.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cj")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CjEntity extends BaseEntity {

    @Column(length = 255)
    private int step;

    @Column(length = 255)
    private int distance;

    @Column(length = 255)
    private int cjBpm;

}
