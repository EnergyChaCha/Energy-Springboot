package com.chacha.energy.api.auth.repository;

import com.chacha.energy.domain.health.entity.Health;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRepository extends JpaRepository<Health, Integer> {
}
