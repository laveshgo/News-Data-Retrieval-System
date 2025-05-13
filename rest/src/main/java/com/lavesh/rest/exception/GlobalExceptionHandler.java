package com.lavesh.rest.exception;

import com.lavesh.common.core.constant.AppConstant;
import com.lavesh.common.core.enums.ErrorCodeEnum;
import com.lavesh.common.core.exception.BaseException;
import com.lavesh.common.core.model.ErrorCode;
import com.lavesh.serving.layer.dto.BaseResponseDto;
import com.lavesh.serving.layer.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {Throwable.class})
    @ResponseBody
    protected ResponseEntity<BaseResponseDto<Object>> handleConflict(Throwable throwableException) {
        if (throwableException instanceof BaseException) {
            return ResponseUtil.buildErrorResponse((BaseException) throwableException);
        }

        if (throwableException.getCause() instanceof BaseException) {
            return ResponseUtil.buildErrorResponse((BaseException) throwableException.getCause());
        }

        if (throwableException instanceof AccessDeniedException) {
            return ResponseUtil.buildErrorResponse(new BaseException(
                    new ErrorCode(ErrorCodeEnum.UNAUTHORIZED, AppConstant.RESOURCE_ACCESS_NOT_ALLOWED)));
        }

        if (throwableException.getCause() instanceof AccessDeniedException) {
            return ResponseUtil.buildErrorResponse(new BaseException(
                    new ErrorCode(ErrorCodeEnum.UNAUTHORIZED, AppConstant.RESOURCE_ACCESS_NOT_ALLOWED)));
        }

        if (throwableException instanceof DataIntegrityViolationException) {
            return ResponseUtil.buildErrorResponse(new BaseException(
                    new ErrorCode(ErrorCodeEnum.DATA_INTEGRITY_VIOLATED, throwableException.getMessage())));
        }

        if (throwableException.getCause() instanceof DataIntegrityViolationException) {
            return ResponseUtil.buildErrorResponse(new BaseException(
                    new ErrorCode(ErrorCodeEnum.DATA_INTEGRITY_VIOLATED, throwableException.getCause().getMessage())));
        }

        if (throwableException instanceof MissingServletRequestParameterException) {
            return ResponseUtil.buildErrorResponse(new BaseException(
                    new ErrorCode(ErrorCodeEnum.BAD_REQUEST, throwableException.getMessage())));
        }

        if (throwableException.getCause() instanceof MissingServletRequestParameterException) {
            return ResponseUtil.buildErrorResponse(new BaseException(
                    new ErrorCode(ErrorCodeEnum.BAD_REQUEST, throwableException.getCause().getMessage())));
        }

        if (throwableException instanceof MethodArgumentTypeMismatchException) {
            return ResponseUtil.buildBadRequestResponseWithCustomMessage(ErrorCodeEnum.BAD_REQUEST, AppConstant.YOUR_REQUEST_CAN_NOT_PROCESS);
        }

        log.error(AppConstant.EXCEPTION_PRINT_FORMAT, ExceptionUtils.getMessage(throwableException),
                ExceptionUtils.getStackTrace(throwableException));
        return ResponseUtil.buildErrorResponse(new BaseException(new ErrorCode(
                ErrorCodeEnum.INTERNAL_SERVER_ERROR, AppConstant.UNKNOWN_ERROR_HAPPENED)));
    }
}
