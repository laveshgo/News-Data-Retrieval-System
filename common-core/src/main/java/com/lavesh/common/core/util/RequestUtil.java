package com.lavesh.common.core.util;

import com.lavesh.common.core.constant.AppConstant;
import org.apache.commons.lang3.ObjectUtils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getHeaderValue(final HttpServletRequest httpServletRequest, final String headerName) {
        if (ObjectUtils.isEmpty(httpServletRequest)) {
            return null;
        }

        return httpServletRequest.getHeader(headerName);
    }

    public static String getFullUrl(final HttpServletRequest httpServletRequest) {
        if (ObjectUtils.isEmpty(httpServletRequest)) {
            return null;
        }

        StringBuilder requestUrl = new StringBuilder(httpServletRequest.getRequestURL().toString());
        String queryString = httpServletRequest.getQueryString();

        if (ObjectUtils.isEmpty(queryString)) {
            return requestUrl.toString();
        }
        return requestUrl.append(AppConstant.QUESTION_MARK).append(queryString).toString();
    }

    public static String getMethod(final HttpServletRequest httpServletRequest) {
        if (ObjectUtils.isEmpty(httpServletRequest)) {
            return null;
        }

        return httpServletRequest.getMethod();
    }

}
