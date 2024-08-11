package com.chacha.energy.cj.dto;

import com.chacha.energy.cj.util.Aes256Util;
import lombok.*;

public class ActivityMetricDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class staffListDtoResponse {
        private int memberId;
        private String name;
        private Integer bpm;
        private Integer step;
        private Double distance;

        public staffListDtoResponse(int memberId, String name, String bpm, Integer step, Double distance) {
            this.memberId = memberId;
            this.name = name;
            this.bpm = Aes256Util.decryptBpm(bpm);
            this.step = step;
            this.distance = distance;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class staffBpmSaveRequest {
        private int memberId;
        private Integer bpm;
        private Integer step;
        private Double distance;
    }
}
