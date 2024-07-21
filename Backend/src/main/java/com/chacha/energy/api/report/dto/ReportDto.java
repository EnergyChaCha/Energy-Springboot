package com.chacha.energy.api.report.dto;

import com.chacha.energy.common.util.MaskingUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        @Schema(description = "시작날짜", nullable = false, example = "2024-07-07")
        private String start;
        @Schema(description = "끝날짜", nullable = false, example = "2024-07-07")
        private String end;
        private int page;
        private int size;
        private int flag;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestReport {
        private int patientId;
        private String status;
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

    @Getter
    @NoArgsConstructor
    public static class ResponseFlagInfo {
        private String name = "";
        private String gender= "";
        private String workArea= "";
        private String department= "";
        private String status= "";

        @Builder
        public ResponseFlagInfo(String name, Boolean gender, String workArea, String department, String status) {
            this.name = MaskingUtil.maskName(name);
            this.gender = "여성";
            if (gender) {
                this.gender = "남성";
            }
            this.workArea = workArea;
            this.department = department;
            this.status = status;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ResponseMyReportItem {
        private Integer reportId;
        private LocalDateTime createdTime;
        private Integer bpm;
        private String gps;
        private String flag;
        private ResponseFlagInfo flagInfo;

        @Builder
        public ResponseMyReportItem(Integer reportId, LocalDateTime createdTime, Integer bpm, Double latitude, Double longitude, String flag, ResponseFlagInfo flagInfo) {
            this.reportId = reportId;
            this.createdTime = createdTime;
            this.bpm = bpm;
            this.gps = latitude + ", " + longitude;
            this.flag = flag;
            this.flagInfo = flagInfo;
        }

        @Builder
        public ResponseMyReportItem(ResponseReportFlagInfoDto responseReportFlagInfoDto, String flag, ResponseFlagInfo flagInfo) {
            this(responseReportFlagInfoDto.getReportId(), responseReportFlagInfoDto.getCreatedTime(), responseReportFlagInfoDto.getBpm(), responseReportFlagInfoDto.getLatitude(), responseReportFlagInfoDto.getLatitude(), flag, flagInfo);
        }
    }

    @Getter
    public static class ResponseMyReportList {
        private List<ResponseMyReportItem> report;

        public ResponseMyReportList() {
            this.report = new ArrayList<>();
        }

        public ResponseMyReportList(List<ReportDto.ResponseMyReportItem> responseMyReportItem) {
            report = responseMyReportItem;
        }
    }





}
