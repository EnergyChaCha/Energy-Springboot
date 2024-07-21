package com.chacha.energy.api.report.repository;

import com.chacha.energy.api.report.dto.ReportDto;
import com.chacha.energy.api.report.dto.ResponseReportDto;
import com.chacha.energy.api.report.dto.ResponseReportFlagInfoDto;
import com.chacha.energy.domain.report.entity.Report;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query("select new com.chacha.energy.api.report.dto.ResponseReportDto(r.id, r.reporter.id, r.patient.id, r.confirmer.id, r.status, r.bpm, r.latitude, r.longitude)" +
            " from Report r WHERE r.createdTime >= :start AND r.createdTime <= :end")
    Page<ResponseReportDto> findAllByTime(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

//    @Query("select new com.chacha.energy.api.report.dto.ResponseReportDto(r.id, r.reporter.id, r.patient.id, r.confirmer.id, r.status, r.bpm, r.latitude, r.longitude) " +
//            " from Report r WHERE (r.reporter.id = :id) AND (r.createdTime BETWEEN :start AND :end) ")
//    Page<ResponseReportDto> findMyReportList(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("id") int id, Pageable pageable);

    @Query("select new com.chacha.energy.api.report.dto.ResponseReportFlagInfoDto(r.id, r.createdTime, r.bpm, r.latitude, r.longitude, r.patient.name, r.patient.gender, r.patient.workArea, r.patient.department, r.status, r.reporter.id, r.patient.id) " +
            " from Report r WHERE ((r.reporter.id = :id) AND (r.createdTime BETWEEN :start AND :end)) OR ((r.patient.id = :id) AND (r.createdTime BETWEEN :start AND :end)) ")
    Page<ResponseReportFlagInfoDto> findEveryMyReportList(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("id") int id, Pageable pageable);
}
