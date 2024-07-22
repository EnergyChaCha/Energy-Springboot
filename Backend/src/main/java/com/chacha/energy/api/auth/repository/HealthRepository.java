package com.chacha.energy.api.auth.repository;

import com.chacha.energy.domain.health.entity.Health;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRepository extends JpaRepository<Health, Integer> {

    @Query("SELECT h FROM Health h WHERE h.member.id = :id")
    Optional<Health> findByMemberId(@Param("id") int id);
}
