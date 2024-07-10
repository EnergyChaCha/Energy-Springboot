package com.chacha.energy.domain.heartRate.repository;

import com.chacha.energy.domain.heartRate.entity.HeartRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRateRepository extends JpaRepository<HeartRate, Integer> {
}
