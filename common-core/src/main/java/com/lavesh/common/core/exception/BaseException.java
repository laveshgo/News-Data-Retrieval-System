package com.lavesh.common.core.exception;


import com.lavesh.common.core.model.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseException extends Throwable {
    private ErrorCode errorCode;
}


