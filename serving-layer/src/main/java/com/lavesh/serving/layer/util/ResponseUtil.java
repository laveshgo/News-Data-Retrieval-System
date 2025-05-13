package com.lavesh.serving.layer.util;

import com.lavesh.common.core.constant.AppConstant;
import com.lavesh.common.core.exception.BaseException;
import com.lavesh.common.core.util.AppUtil;
import com.lavesh.serving.layer.dto.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResponseUtil {

    public static ResponseEntity<BaseResponseDto<Object>> buildBadRequestResponse() {
        Map<String, String> badRequestMessageMap = new HashMap<>();
        badRequestMessageMap.put("message", "Mandatory params missing or wrong values");
        BaseResponseDto<Object> baseResponseDto = new BaseResponseDto<>();
        baseResponseDto.setTimestamp(AppUtil.getStartTimeStampFromMDC());
        baseResponseDto.setRequestId(AppUtil.getTransactionIdFromMdc());
        baseResponseDto.setHostName(AppUtil.getHostName());
        baseResponseDto.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponseDto.setStatusCode(HttpStatus.BAD_REQUEST);
        baseResponseDto.setStatusSubCode(HttpStatus.BAD_REQUEST.name());
        baseResponseDto.setMessage(AppConstant.VALIDATION_FAILED);
        baseResponseDto.setResponse(badRequestMessageMap);
        return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<BaseResponseDto<Object>> buildBadRequestResponse(Object response) {
        BaseResponseDto<Object> baseResponseDto = new BaseResponseDto<>();
        baseResponseDto.setTimestamp(AppUtil.getStartTimeStampFromMDC());
        baseResponseDto.setRequestId(AppUtil.getTransactionIdFromMdc());
        baseResponseDto.setHostName(AppUtil.getHostName());
        baseResponseDto.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponseDto.setStatusCode(HttpStatus.BAD_REQUEST);
        baseResponseDto.setStatusSubCode(HttpStatus.BAD_REQUEST.name());
        baseResponseDto.setMessage(AppConstant.VALIDATION_FAILED);
        baseResponseDto.setResponse(response);
        return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<BaseResponseDto<Object>> buildSuccessResponse(Object response) {
        BaseResponseDto<Object> baseResponseDto = new BaseResponseDto<>();
        baseResponseDto.setTimestamp(AppUtil.getStartTimeStampFromMDC());
        baseResponseDto.setRequestId(AppUtil.getTransactionIdFromMdc());
        baseResponseDto.setHostName(AppUtil.getHostName());
        baseResponseDto.setStatus(HttpStatus.OK.value());
        baseResponseDto.setStatusCode(HttpStatus.OK);
        baseResponseDto.setStatusSubCode(HttpStatus.OK.name());
        baseResponseDto.setMessage(AppConstant.SUCCESSFULLY_PROCESSED);
        baseResponseDto.setResponse(response);
        return new ResponseEntity<>(baseResponseDto, HttpStatus.OK);
    }

    public static ResponseEntity<BaseResponseDto<Object>> buildSuccessResponse() {
        Map<String, String> successMessageMap = new HashMap<>();
        successMessageMap.put("message", AppConstant.SUCCESSFULLY_PROCESSED);

        BaseResponseDto<Object> baseResponseDto = new BaseResponseDto<>();
        baseResponseDto.setTimestamp(AppUtil.getStartTimeStampFromMDC());
        baseResponseDto.setRequestId(AppUtil.getTransactionIdFromMdc());
        baseResponseDto.setHostName(AppUtil.getHostName());
        baseResponseDto.setStatus(HttpStatus.OK.value());
        baseResponseDto.setStatusCode(HttpStatus.OK);
        baseResponseDto.setStatusSubCode(HttpStatus.OK.name());
        baseResponseDto.setMessage(AppConstant.SUCCESSFULLY_PROCESSED);
        baseResponseDto.setResponse(successMessageMap);
        return new ResponseEntity<>(baseResponseDto, HttpStatus.OK);
    }


    public static ResponseEntity<BaseResponseDto<Object>> buildErrorResponse(BaseException baseException) {
        Map<String, String> successMessageMap = new HashMap<>();
        successMessageMap.put("message", baseException.getErrorCode().getMessage());

        BaseResponseDto<Object> baseResponseDto = new BaseResponseDto<>();
        baseResponseDto.setTimestamp(AppUtil.getStartTimeStampFromMDC());
        baseResponseDto.setRequestId(AppUtil.getTransactionIdFromMdc());
        baseResponseDto.setHostName(AppUtil.getHostName());
        baseResponseDto.setStatus(baseException.getErrorCode().getErrorCodeEnum().getHttpStatus().value());
        baseResponseDto.setStatusCode(baseException.getErrorCode().getErrorCodeEnum().getHttpStatus());
        baseResponseDto.setStatusSubCode(baseException.getErrorCode().getErrorCodeEnum().name());
        baseResponseDto.setMessage(baseException.getErrorCode().getMessage());
        baseResponseDto.setResponse(successMessageMap);
        return new ResponseEntity<>(baseResponseDto, baseException.getErrorCode().getErrorCodeEnum().getHttpStatus());
    }

    public static ResponseEntity<BaseResponseDto<Object>> buildBadRequestResponseWithCustomMessage(Object response, String message) {
        BaseResponseDto<Object> baseResponseDto = new BaseResponseDto<>();
        baseResponseDto.setTimestamp(AppUtil.getStartTimeStampFromMDC());
        baseResponseDto.setRequestId(AppUtil.getTransactionIdFromMdc());
        baseResponseDto.setHostName(AppUtil.getHostName());
        baseResponseDto.setStatus(HttpStatus.BAD_REQUEST.value());
        baseResponseDto.setStatusCode(HttpStatus.BAD_REQUEST);
        baseResponseDto.setStatusSubCode(HttpStatus.BAD_REQUEST.name());
        baseResponseDto.setMessage(message);
        baseResponseDto.setResponse(response);
        return new ResponseEntity<>(baseResponseDto, HttpStatus.BAD_REQUEST);
    }
}
