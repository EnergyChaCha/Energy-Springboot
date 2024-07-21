package com.chacha.energy.api.heartRate.service;

import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.api.heartRate.dto.HeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseHeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseListHeartRateDto;
import com.chacha.energy.api.heartRate.repository.HeartRateRepository;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.report.entity.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    // HT-02 심박수 임계치 조회
    public HeartRateDto.UpdateHeartRateThresholdResponse getHeartRateByMemberId(int id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

        return HeartRateDto.UpdateHeartRateThresholdResponse.builder()
                .minTreshold(member.getMinBpmThreshold())
                .maxTreshold(member.getMaxBpmThreshold())
                .build();
    }

    // HT-01 심박수 임계치 업데이트
    public HeartRateDto.UpdateHeartRateThresholdResponse updateHeartRateThreshold(int id, int minThreshold, int maxTreshold) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

        member.setMinBpmThreshold(minThreshold);
        member.setMaxBpmThreshold(maxTreshold);

        Member updatedMember = memberRepository.save(member);

        return HeartRateDto.UpdateHeartRateThresholdResponse.builder()
                .minTreshold(member.getMinBpmThreshold())
                .maxTreshold(member.getMaxBpmThreshold())
                .build();
    }


    // HI-09 심박 수 저장
    public HeartRateDto.PostHearRateResponse saveHeartRate(int id, int bpm) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

        boolean overHeartRate = false;
        String heartStatus = "stable";
        if(bpm<member.getMinBpmThreshold() || bpm>member.getMaxBpmThreshold()){
            heartStatus = "emergency";
            overHeartRate = true;
        }

        else if(bpm-member.getMinBpmThreshold() <= 10 || member.getMaxBpmThreshold()-bpm <= 10){
            heartStatus = "caution";
        }
        HeartRate heartRate = new HeartRate(
                member,
                bpm,
                overHeartRate,
                heartStatus
        );
        heartRate = heartRateRepository.save(heartRate);

        return HeartRateDto.PostHearRateResponse.builder()
                .bpm(heartRate.getBpm())
                .overThreshold(heartRate.getExceedsThreshold())
                .build();
    }


    // HI-01 심박수 그래프 조회
    public List<ResponseHeartRateDto> getGraphByHeartRate(int id, LocalDateTime start, LocalDateTime end, String interval){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

       List<ResponseHeartRateDto> responseHeartRateDto = heartRateRepository.findHeartRateStatistics(id, start, end, interval);

        return responseHeartRateDto;
    }

    // HI-02 전체 심박수임계치 리스트 조회
    public List<ResponseListHeartRateDto> getAllHeartRates(LocalDateTime start, LocalDateTime end, String heartrateStatus, String loginId) {
        return heartRateRepository.findAllHeartRateThresholds(start, end, heartrateStatus, loginId);
    }


    // HI-03 회원별 심박수 삼세조회
//    public HeartRateDto.GetDetailMemberHeartrateResponse getMemberHeartRateDetail(int id) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new CustomException(ErrorCode.NO_ID));
//
//        HeartRate latestHeartRate = heartRateRepository.findLatestByMemberId(member.getId())
//                .orElse(null);
//
//        return HeartRateDto.GetDetailMemberHeartrateResponse.builder()
//                .name(member.getName())
//                .birthdate(member.getBirthdate())
//                .gender(member.getGender())
//                .status(latestHeartRate != null ? latestHeartRate.getBpm() : 0)
//                .phone(member.getPhone())
//                .loginId(member.getLoginId())
//                .workArea(member.getWorkArea())
//                .department(member.getDepartment())
//                .build();
//    }

    // HI-04 심박수 상세조회
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
