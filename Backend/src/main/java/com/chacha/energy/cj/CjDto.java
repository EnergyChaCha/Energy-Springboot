package com.chacha.energy.cj;

import lombok.*;

public class CjDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CjDtoResponse{
        private int name;
        private int createTime;
        private int bpm;
        private int step;
        private int distance;
    }
}
