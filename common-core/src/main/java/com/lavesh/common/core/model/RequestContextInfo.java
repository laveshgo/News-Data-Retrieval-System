package com.lavesh.common.core.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RequestContextInfo {

    private String logId;
    private String hostName;
    private ApiDetails apiDetails;
    private Timestamp startDate;
    private Timestamp endDate;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiDetails {
        private String method;
        private String url;
    }
}
