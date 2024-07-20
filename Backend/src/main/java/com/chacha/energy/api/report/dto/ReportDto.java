package com.chacha.energy.api.report.dto;

import lombok.*;

import java.time.LocalDate;

public class ReportDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestAllReports {
        private String start;
        private String end;
        private String loginId;
        private int page;
        private int size;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestMyReportList {
        private String start;
        private String end;
        private int page;
        private int size;
        private int flag;
        /* TO-BE : JWT에서 ID를 가져올 수 있도록 수정해야 함 */
        private int id;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestReport {
        /* TO-BE : JWT에서 ID를 가져올 수 있도록 수정해야 함 */
        private int reporterId;
        private int patientId;
        private int status;
        private int heartRate;
        private double latitude;
        private double longitude;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestConfirm {
        /* TO-BE : JWT에서 ID를 가져올 수 있도록 수정해야 함 */
        private int reportId;
        private int confirmerId;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestSearchOther {
        private String name;
        private String workArea;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseSearchResult {
        private int id;
        private String name;
        private String phone;
        private String loginId;
        private boolean gender;
        private String workArea;
        private String department;
        private LocalDate birthdate;
    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseMyInfo {
        private int id;
        private String name;
        private String phone;
        private String workArea;
        private String department;
    }

}
