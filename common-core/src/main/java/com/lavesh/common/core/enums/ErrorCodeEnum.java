package com.lavesh.common.core.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCodeEnum {

    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    NOT_SUPPORTED(HttpStatus.NOT_FOUND),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
    DATA_INTEGRITY_VIOLATED(HttpStatus.UNPROCESSABLE_ENTITY);

    private final HttpStatus httpStatus;

    ErrorCodeEnum(final HttpStatus httpStatus) {

        this.httpStatus = httpStatus;
    }
}
