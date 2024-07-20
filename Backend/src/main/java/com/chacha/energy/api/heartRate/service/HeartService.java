package com.chacha.energy.api.heartRate.service;

import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.api.heartRate.dto.HeartRateDto;
import com.chacha.energy.api.heartRate.repository.HeartRateRepository;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.heartStatus.entity.HeartStatus;
import com.chacha.energy.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HeartService {

    private final MemberRepository memberRepository;
    private final HeartRateRepository heartRateRepository;

//    @Autowired
//    public HeartRateService(MemberRepository memberRepository, HeartRateRepository heartRateRepository) {
//        this.memberRepository = memberRepository;
//        this.heartRateRepository = heartRateRepository;
//    }

    public Map<String, Integer> getHeartRateByMemberId(int id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

        Map<String, Integer> threshold = new HashMap<>();
        threshold.put("minThreshold", member.getMinBpmThreshold());
        threshold.put("maxThreshold", member.getMaxBpmThreshold());

        return threshold;
    }

    // HI-02 심박수 업데이트
    public Map<String, Integer> updateHeartRateThreshold(int id, int minThreshold, int maxTreshold) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

        member.setMinBpmThreshold(minThreshold);
        member.setMaxBpmThreshold(maxTreshold);

        Member updatedMember = memberRepository.save(member);

        Map<String, Integer> threshold = new HashMap<>();
        threshold.put("minThreshold", updatedMember.getMinBpmThreshold());
        threshold.put("maxThreshold", updatedMember.getMaxBpmThreshold());

        return threshold;
    }

    // HI-03 회원별 심박수 삼세조회
    public HeartRateDto.GetDetailMemberHeartrateResponse getMemberHeartRateDetail(int id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

        HeartRate latestHeartRate = heartRateRepository.findLatestByMemberId(member.getId())
                .orElse(null);

        return HeartRateDto.GetDetailMemberHeartrateResponse.builder()
                .name(member.getName())
                .birthdate(member.getBirthdate())
                .gender(member.getGender())
                .status(latestHeartRate != null ? latestHeartRate.getHeartRate() : 0)
                .phone(member.getPhone())
                .loginId(member.getLoginId())
                .workArea(member.getWorkArea())
                .department(member.getDepartment())
                .build();
    }

//    // HI-04 심박수 상세조회
//    public HeartRateDto.GetHeartRateAvg getHeartRateStatistics(LocalDateTime start, LocalDateTime end) {
//
//        HeartRateDto.GetHeartRateAvg heartRateAvg = memberRepository.getHeartRateStatsBetweenDates(start,end);
//
//        return HeartRateDto.GetHeartRateAvg.builder()
//                .minBpmThreshold(heartRateAvg.getMinBpmThreshold())
//                .maxBpmThreshold(heartRateAvg.getMaxBpmThreshold())
//                .averageThreshold(Math.round(heartRateAvg.getAverageThreshold()))
//                .build();
//    }
}
