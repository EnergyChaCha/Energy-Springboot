package com.chacha.energy.api.heartRate.service;

import com.chacha.energy.api.auth.dto.AuthDto;
import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.api.heartRate.dto.HeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseHeartRateAvg;
import com.chacha.energy.api.heartRate.dto.ResponseHeartRateDto;
import com.chacha.energy.api.heartRate.dto.ResponseListHeartRateDto;
import com.chacha.energy.api.heartRate.repository.HeartRateRepository;
import com.chacha.energy.api.heartRate.repository.HeartStatusRepository;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import com.chacha.energy.common.util.MaskingUtil;
import com.chacha.energy.domain.heartRate.entity.HeartRate;
import com.chacha.energy.domain.heartStatus.entity.HeartStatus;
import com.chacha.energy.domain.member.entity.Member;
import com.chacha.energy.domain.member.service.MemberService;
import com.chacha.energy.domain.report.entity.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HeartService {

    private final MemberRepository memberRepository;
    private final HeartRateRepository heartRateRepository;
    private final HeartStatusRepository heartStatusRepository;
    private final MemberService memberService;

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
    public HeartRateDto.PostHearRateResponse saveHeartRate(int bpm) {
        Member member = memberService.getLoginMember();

        boolean overHeartRate = false;
        int heartStatus = 0;
        if(bpm<member.getMinBpmThreshold() || bpm>member.getMaxBpmThreshold()){
            heartStatus = 2;
            overHeartRate = true;
        }

        else if(bpm-member.getMinBpmThreshold() <= 10 || member.getMaxBpmThreshold()-bpm <= 10){
            heartStatus = 1;
        }
        HeartRate heartRate = new HeartRate(
                member,
                bpm,
                overHeartRate,
                heartStatus
        );
        heartRate = heartRateRepository.save(heartRate);

        // TODO: 심박수 초과이며 해당 멤버의 가장 최신 alert가 5분 이전인 경우 alert 추가

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
    public List<ResponseListHeartRateDto> getAllHeartRates(LocalDateTime start, LocalDateTime end, Integer heartrateStatus, String loginId) {
        return heartRateRepository.findAllHeartRateThresholds(start, end, heartrateStatus, loginId);
    }

    public List<ResponseListHeartRateDto> getAllHeartRatesMasking(LocalDateTime start, LocalDateTime end, Integer heartrateStatus, String loginId) {
        List<ResponseListHeartRateDto> heartRates = heartRateRepository.findAllHeartRateThresholds(start, end, heartrateStatus, loginId);
        for (ResponseListHeartRateDto dto: heartRates) {
            dto.setLoginId(MaskingUtil.maskLoginId(dto.getLoginId()));
            dto.setName(MaskingUtil.maskName(dto.getName()));
            dto.setPhone(MaskingUtil.maskPhone(dto.getPhone()));
        }

//        for (ResponseListHeartRateDto heartRate : heartRates) {
//            heartRate.setName(MaskingUtil.maskName(heartRate.getName()));
//            heartRate.setPhone(MaskingUtil.maskPhone(heartRate.getPhone()));
//            heartRate.setLoginId(MaskingUtil.maskLoginId(heartRate.getLoginId()));
//        }
        return heartRates;
    }

//     HI-03 회원별 심박수 삼세조회
    public AuthDto.GetDetailMemberHeartrateResponse getMemberHeartRateDetail(int id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_ID));

        HeartStatus latestHeartStatus = heartStatusRepository.findTopByMemberOrderByCreatedTimeDesc(member)
                .orElse(null);

        return AuthDto.GetDetailMemberHeartrateResponse.builder()
                .name(member.getName())
                .birthdate(member.getBirthdate())
                .gender(member.getGender())
                .status(latestHeartStatus != null ? latestHeartStatus.getStatus() : 0)
                .phone(member.getPhone())
                .loginId(member.getLoginId())
                .workArea(member.getWorkArea())
                .department(member.getDepartment())
                .build();
    }

//     HI-04 심박수 상세조회
    public List<ResponseHeartRateAvg> getHeartRateStatistics(int id, LocalDateTime start, LocalDateTime end) {

        List<ResponseHeartRateAvg> responseHeartRateAvgs = heartRateRepository.findHeartRateStatistics(id, start, end);

        return responseHeartRateAvgs;
    }
}
