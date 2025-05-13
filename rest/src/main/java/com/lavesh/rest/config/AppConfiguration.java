package com.lavesh.rest.config;

import com.lavesh.common.core.constant.AppConstant;
import com.lavesh.rest.interceptor.RequestContextInfoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestContextInfoInterceptor requestContextInfoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestContextInfoInterceptor).addPathPatterns(AppConstant.ALL_PATH_PATTERN);
    }
}
