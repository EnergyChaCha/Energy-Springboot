package com.chacha.energy.cj;

import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.api.report.dto.ReportDto;
import com.chacha.energy.api.report.dto.ResponseAllReportDto;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CjService {
    private final CjRepository cjRepository;
    private final MemberRepository memberRepository;

    public LocalDateTime convertStringToTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, formatter).atStartOfDay();
    }

    public Page<CjDto.staffListDtoResponse> searchWorkersByName(String name, Integer bpm, Integer step, Double distance,
    Integer page, Integer size, String start, String end) {
        PageRequest pageRequest = PageRequest.of(page, size);

        LocalDateTime startTime = convertStringToTime(start);
        LocalDateTime endTime = convertStringToTime(end);

        return cjRepository.findByMemberNameContaining(name, bpm, step, distance, startTime, endTime, pageRequest);
    }

    public CjDto.staffListDtoResponse saveBpm(CjDto.staffBpmSaveRequest staffBpmSaveRequest){
        LocalDateTime localDateTime = LocalDateTime.now();
        CjEntity cjEntity = cjRepository.existsByCurrentDate(staffBpmSaveRequest.getMemberId(),localDateTime);
        if(cjEntity==null){
            cjEntity = new CjEntity(memberRepository.findById(staffBpmSaveRequest.getMemberId()).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_ID, staffBpmSaveRequest.getMemberId())
                    ),
                    staffBpmSaveRequest.getStep(),
                    staffBpmSaveRequest.getDistance(),
                    staffBpmSaveRequest.getBpm());
        }
        else{
            cjEntity.setBpm(staffBpmSaveRequest.getBpm());
            cjEntity.setStep(staffBpmSaveRequest.getStep());
            cjEntity.setDistance(staffBpmSaveRequest.getDistance());
        }
        cjRepository.save(cjEntity);

        return CjDto.staffListDtoResponse.builder()
                .memberId(cjEntity.getMember().getId())
                .name(cjEntity.getMember().getName())
                .bpm(cjEntity.getBpm())
                .distance(cjEntity.getDistance())
                .step(cjEntity.getStep())
                .build();
    }

}

