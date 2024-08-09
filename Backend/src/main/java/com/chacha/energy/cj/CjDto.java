package com.chacha.energy.cj;

import lombok.*;

public class CjDto {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CjDtoResponse{
        private String name;
        private int createTime;
        private int cjBpm;
        private int step;
        private int distance;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class staffListDtoResponse{
        private String name;
        private int bpm;
        private int step;
        private int distance;
    }

}
