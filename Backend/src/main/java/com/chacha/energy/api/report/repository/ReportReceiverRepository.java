package com.chacha.energy.api.report.repository;

import com.chacha.energy.domain.reportReceiver.entity.ReportReceiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportReceiverRepository extends JpaRepository<ReportReceiver, Integer> {

}
