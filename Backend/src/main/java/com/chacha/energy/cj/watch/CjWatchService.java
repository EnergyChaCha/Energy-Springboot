package com.chacha.energy.cj.watch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CjWatchService {
    private final CjWatchRepository cjWatchRepository;
    public String postStepCount(CjWatchDto dto) {
        CjWatchEntity entity = new CjWatchEntity(dto.getKey());
        cjWatchRepository.save(entity);
        return "성공";
    }
}
