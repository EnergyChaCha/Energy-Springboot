package com.chacha.energy.cj.service;

import com.chacha.energy.cj.dto.StepCountRequestDto;
import com.chacha.energy.cj.entity.StepCount;
import com.chacha.energy.cj.repository.StepCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StepCountService {
    private final StepCountRepository stepCountRepository;

    public String postStepCount(StepCountRequestDto dto) {
        StepCount entity = new StepCount(dto.getKey());
        stepCountRepository.save(entity);
        return "성공";
    }
}
