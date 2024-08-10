package com.chacha.energy.cj;

import lombok.*;

public class CjDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class staffListDtoResponse{
        private int memberId;
        private String name;
        private Integer bpm;
        private Integer step;
        private Double distance;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class staffBpmSaveRequest{
        private int memberId;
        private Integer bpm;
        private Integer step;
        private Double distance;
    }
}
