package com.lavesh.common.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<R> {

    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("statusCode")
    private HttpStatus statusCode;

    @JsonProperty("statusSubCode")
    private String statusSubCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("hostName")
    private String hostName;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("response")
    private R response;
}

