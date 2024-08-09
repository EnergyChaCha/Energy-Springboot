package com.chacha.energy.cj;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CjService {
    private final CjRepository cjRepository;

    public List<CjDto.staffListDtoResponse> searchWorkersByName(String name) {
        return cjRepository.findByMemberNameContaining(name);
    }
}
