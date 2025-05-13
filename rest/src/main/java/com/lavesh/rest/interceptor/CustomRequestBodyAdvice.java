package com.lavesh.rest.interceptor;

import com.lavesh.common.core.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@Slf4j
@ControllerAdvice
public class CustomRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(@NotNull final MethodParameter methodParameter, @NotNull final Type targetType,
                            @NotNull final Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @NotNull
    @Override
    public HttpInputMessage beforeBodyRead(@NotNull final HttpInputMessage inputMessage,
                                           @NotNull final MethodParameter parameter,
                                           @NotNull final Type targetType,
                                           @NotNull final Class<? extends HttpMessageConverter<?>> converterType) {
        return inputMessage;
    }

    @NotNull
    @Override
    public Object afterBodyRead(@NotNull final Object body, @NotNull final HttpInputMessage inputMessage,
                                @NotNull final MethodParameter parameter, @NotNull final Type targetType,
                                @NotNull final Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("Request Body {}", AppUtil.toJson(body));
        return body;
    }

    @Override
    public Object handleEmptyBody(final Object body, @NotNull final HttpInputMessage inputMessage,
                                  @NotNull final MethodParameter parameter, @NotNull final Type targetType,
                                  @NotNull final Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("Request Body empty");
        return body;
    }
}
