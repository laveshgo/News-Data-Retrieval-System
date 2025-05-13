package com.lavesh.rest.interceptor;

import com.lavesh.common.core.constant.AppConstant;
import com.lavesh.common.core.model.RequestContextInfo;
import com.lavesh.common.core.util.AppUtil;
import com.lavesh.common.core.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@Slf4j
@Component
public class RequestContextInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestContextInfo requestContextInfo;

    @Override
    public boolean preHandle(final HttpServletRequest httpServletRequest,
                             final HttpServletResponse response, final Object handler) {

        // Set Request Context Info
        setInitValuesInRequestContextInfo(httpServletRequest);

        // Set MDC Info
        setMdcInfoForCurrentThread();

        log.info("PreHandle called for interceptor");
        log.info("Request Method: {} , Request URL: {} ", requestContextInfo.getApiDetails().getMethod(), requestContextInfo.getApiDetails().getUrl());
        log.info("Request Context Info Final {}", AppUtil.toJson(requestContextInfo));
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception exception) throws Exception {

        requestContextInfo.setEndDate(new Timestamp(System.currentTimeMillis()));
        log.info("AfterCompletion called for interceptor");
        log.info("Request Method: {} , Request URL: {} , Time taken {}, Response Status: {}",
                requestContextInfo.getApiDetails().getMethod(),
                requestContextInfo.getApiDetails().getUrl(),
                requestContextInfo.getEndDate().getTime() - requestContextInfo.getStartDate().getTime(),
                response.getStatus());

        // Flush MDC Info
        AppUtil.flushMDC();
    }

    // Private Functions
    private void setInitValuesInRequestContextInfo(final HttpServletRequest httpServletRequest) {
        RequestContextInfo.ApiDetails apiDetails = RequestContextInfo.ApiDetails.builder()
                .url(RequestUtil.getFullUrl(httpServletRequest))
                .method(RequestUtil.getMethod(httpServletRequest))
                .build();

        requestContextInfo.setApiDetails(apiDetails);
        requestContextInfo.setLogId(AppUtil.getTransactionIdFromRequestOrGenerateNewTransactionId(httpServletRequest));
        requestContextInfo.setStartDate(new Timestamp(System.currentTimeMillis()));
        requestContextInfo.setHostName(AppUtil.getHostName());
    }

    private void setMdcInfoForCurrentThread() {
        AppUtil.addCustomerIdToMDC(AppConstant.REQUEST_CUSTOMER_ID_INIT_VALUE);
        AppUtil.addTransactionIdToMdc(requestContextInfo.getLogId());
        AppUtil.addHostNameToMDC(requestContextInfo.getHostName());
        AppUtil.addMethodNameToMDC(requestContextInfo.getApiDetails().getMethod());
        AppUtil.addStartTimeStampToMDC(requestContextInfo.getStartDate());
        AppUtil.addEndTimeStampToMDC(requestContextInfo.getEndDate());
    }
}
