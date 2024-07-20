package com.chacha.energy.api.auth.repository;

import com.chacha.energy.domain.health.entity.Health;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<Health, Integer> {
}
