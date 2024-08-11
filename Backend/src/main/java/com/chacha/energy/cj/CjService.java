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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    Integer page, Integer size, String order) {



        // 기본 정렬 순서
        List<String> defaultOrder = Arrays.asList(new String[]{"step","distance","bpm"});
        Map<String, Sort.Direction> sortDirection = new HashMap<>();
        sortDirection.put("asc", Sort.Direction.ASC);
        sortDirection.put("desc", Sort.Direction.DESC);

        String[] tokens = order.split(",");

        // 요소별 정렬 방향
        Map<String, Sort.Direction> sortDirectionByFactor = new HashMap<>();
        // 요소의 순서
        List<String> finalOrder = new ArrayList<>();

        // 입력받은 요소별 정렬 방향 지정
        for (String token: tokens) {
            if (token.equals("")) continue;
            String[] elements  = token.split("-");
            if (elements.length < 2) throw new CustomException(ErrorCode.INCORRECT_DELIMITER, token);
            String factor = elements[0];
            String direction = elements[1];
            if (!defaultOrder.contains(factor)) {
                throw new CustomException(ErrorCode.INCORRECT_ELEMENT, factor);
            }
            if (!sortDirection.containsKey(direction)) {
                throw new CustomException(ErrorCode.INCORRECT_DIRECTION, direction);
            }
            finalOrder.add(factor);
            sortDirectionByFactor.put(factor, sortDirection.get(direction));
        }

        // 입력받지 않은 요소에 대해 기본 정렬 순서에 맞게 정렬 지정
        for (String factor: defaultOrder) {
            if (!finalOrder.contains(factor)){
                finalOrder.add(factor);
                sortDirectionByFactor.put(factor,Sort.Direction.ASC);
            }
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (String factor: finalOrder) {
            System.out.println("정렬순서: "+factor+"="+sortDirectionByFactor.get(factor));
            orders.add(new Sort.Order(sortDirectionByFactor.get(factor), factor));
        }

        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return cjRepository.findByMemberNameContaining(name == null ? "" : name, bpm, step, distance, pageRequest);
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

