package com.chacha.energy.cj.service;

import com.chacha.energy.api.auth.repository.MemberRepository;
import com.chacha.energy.cj.dto.ActivityMetricDto;
import com.chacha.energy.cj.entity.ActivityMetric;
import com.chacha.energy.cj.repository.ActivityMetricRepository;
import com.chacha.energy.cj.repository.CryptoRepository;
import com.chacha.energy.cj.util.Aes256Util;
import com.chacha.energy.common.costants.ErrorCode;
import com.chacha.energy.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ActivityMetricService {

    private static String KEY;
    private final ActivityMetricRepository activityMetricRepository;
    private final MemberRepository memberRepository;
    private final CryptoRepository cryptoRepository;

    @Value("${aes256.key}")
    public void setKEY(String KEY) {
        ActivityMetricService.KEY = KEY;
    }

    public Page<ActivityMetricDto.staffListDtoResponse> searchWorkersByName(String name, Integer bpm, Integer step, Double distance,
                                                                            Integer page, Integer size, String order) {

        // 기본 정렬 순서
        List<String> defaultOrder = Arrays.asList(new String[]{"step", "distance", "bpm"});
        Map<String, Sort.Direction> sortDirection = new HashMap<>();
        sortDirection.put("asc", Sort.Direction.ASC);
        sortDirection.put("desc", Sort.Direction.DESC);

        String[] tokens = order.split(",");

        // 요소별 정렬 방향
        Map<String, Sort.Direction> sortDirectionByFactor = new HashMap<>();
        // 요소의 순서
        List<String> finalOrder = new ArrayList<>();

        // 입력받은 요소별 정렬 방향 지정
        for (String token : tokens) {
            if (token.equals("")) continue;
            String[] elements = token.split("-");
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
        for (String factor : defaultOrder) {
            if (!finalOrder.contains(factor)) {
                finalOrder.add(factor);
                sortDirectionByFactor.put(factor, Sort.Direction.ASC);
            }
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (String factor : finalOrder) {
            orders.add(new Sort.Order(sortDirectionByFactor.get(factor), factor.equals("bpm") ? " decrypted_bpm " : factor));
        }

        Sort sort = Sort.by(orders);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        String key = Hex.encodeHexString(Aes256Util.generateKey().getEncoded());
        return cryptoRepository.findByMemberNameContaining(name == null ? "" : name, bpm, step, distance,key, pageRequest);
    }

    public ActivityMetricDto.staffListDtoResponse saveBpm(ActivityMetricDto.staffBpmSaveRequest staffBpmSaveRequest) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ActivityMetric activityMetric = activityMetricRepository.existsByCurrentDate(staffBpmSaveRequest.getMemberId(), localDateTime);
        if (activityMetric == null) {
            activityMetric = new ActivityMetric(memberRepository.findById(staffBpmSaveRequest.getMemberId()).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_ID, staffBpmSaveRequest.getMemberId())
            ),
                    staffBpmSaveRequest.getStep(),
                    staffBpmSaveRequest.getDistance(),
                    staffBpmSaveRequest.getBpm(),
                    Aes256Util.encrypt(staffBpmSaveRequest.getBpm().toString()));
        } else {
            activityMetric.setBpm(Aes256Util.encrypt(staffBpmSaveRequest.getBpm().toString()));
            activityMetric.setStep(staffBpmSaveRequest.getStep());
            activityMetric.setDistance(staffBpmSaveRequest.getDistance());
        }
        activityMetricRepository.save(activityMetric);

        return ActivityMetricDto.staffListDtoResponse.builder()
                .memberId(activityMetric.getMember().getId())
                .name(activityMetric.getMember().getName())
                .bpm(Aes256Util.decryptBpm(activityMetric.getBpm()))
                .distance(activityMetric.getDistance())
                .step(activityMetric.getStep())
                .build();
    }

    public String encodeBpm() {
        String key = Hex.encodeHexString(Aes256Util.generateKey().getEncoded());
        cryptoRepository.encryptAllBpm(key);
        return "성공";
    }
}

