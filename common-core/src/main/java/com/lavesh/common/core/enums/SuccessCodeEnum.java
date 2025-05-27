package com.lavesh.common.core.enums;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCodeEnum {
    NO_CONTENT(HttpStatus.NO_CONTENT),
    SUCCESSFULLY_PROCESSED(HttpStatus.OK);
    private final HttpStatus httpStatus;

    SuccessCodeEnum(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}