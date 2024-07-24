package com.chacha.energy.api.heartRate.repository;

import com.chacha.energy.domain.alertReceiver.entity.AlertReceiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertReceiverRepository extends JpaRepository<AlertReceiver, Integer> {
}
