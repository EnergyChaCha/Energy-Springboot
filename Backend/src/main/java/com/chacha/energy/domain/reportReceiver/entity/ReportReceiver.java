//package com.chacha.energy.domain.reportReceiver.entity;
//
//import com.chacha.energy.common.entity.BaseEntity;
//import com.chacha.energy.domain.member.entity.Member;
//import com.chacha.energy.domain.report.entity.Report;
//import jakarta.persistence.Entity;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "report_receiver")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class ReportReceiver extends BaseEntity {
//
//// TODO: 주석 삭제
//// BaseEntity에 createdTime, updatedTime 있어요~
////    private LocalDateTime createdAt;
////
////    private LocalDateTime updatedAt;
//
//    @ManyToOne
//    private Member receiver;
//
//    @ManyToOne
//    @JoinColumn(name = "신고 내용")
//    private Report report;
//
//}
