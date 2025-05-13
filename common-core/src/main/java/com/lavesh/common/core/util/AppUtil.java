package com.lavesh.common.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lavesh.common.core.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import jakarta.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class AppUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private static final Gson gson = new GsonBuilder()
            .create();

    public static String getUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static String toJson(Object object) {
        if (ObjectUtils.isEmpty(object)) {
            return AppConstant.EMPTY_STRING;
        }

        try {
            return gson.toJson(object);
        } catch (Exception exception) {
            return AppConstant.EMPTY_STRING;
        }
    }

    public static <T> T readStringValueToClass(String value, Class<T> tClass) {
        return gson.fromJson(value, tClass);
    }

    public static <T> T readStringValueToType(String value, Type type) {
        return gson.fromJson(value, type);
    }

    public static void copyNonNullProperties(Object source, Object destination) {
        BeanUtils.copyProperties(source, destination, getNullPropertyNames(source));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper sourceBeanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptorArray = sourceBeanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
            Object srcValue = sourceBeanWrapper.getPropertyValue(propertyDescriptor.getName());
            if (ObjectUtils.isEmpty(srcValue)) emptyNames.add(propertyDescriptor.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    public static String getTransactionIdFromMdc() {
        return MdcUtil.getTransactionIdFromMdc();
    }

    public static String getTransactionIdFromRequestOrGenerateNewTransactionId(final HttpServletRequest httpServletRequest) {
        String requestId = RequestUtil.getHeaderValue(httpServletRequest, AppConstant.REQUEST_LOG_ID);
        if (StringUtils.isEmpty(requestId)) {
            requestId = getUniqueId();
        }
        return requestId;
    }

    public static void addTransactionIdToMdc(final HttpServletRequest httpServletRequest) {
        String requestId = getTransactionIdFromRequestOrGenerateNewTransactionId(httpServletRequest);
        MdcUtil.addTransactionIdToMdc(requestId);
    }
    private static String hostName;
    public static String getHostName() {
        if (ObjectUtils.isNotEmpty(hostName)) {
            return hostName;
        }

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            hostName = inetAddress.getHostName();
            return hostName;
        } catch (UnknownHostException unknownHostException) {
            return AppConstant.EMPTY_STRING;
        }
    }

    public static void addTransactionIdToMdc(final String uniqueId) {
        MdcUtil.addTransactionIdToMdc(uniqueId);
    }

    public static void addCustomerIdToMDC(final String customerId) {
        MdcUtil.addCustomerIdToMDC(customerId);
    }

    public static void addHostNameToMDC(final String hostName) {
        MdcUtil.addHostNameToMDC(hostName);
    }

    public static void addMethodNameToMDC(final String methodName) {
        MdcUtil.addMethodNameToMDC(methodName);
    }

    public static void addStartTimeStampToMDC(final Timestamp startTimeStamp) {
        MdcUtil.addStartTimeStampToMDC(startTimeStamp);
    }

    public static void addEndTimeStampToMDC(final Timestamp endTimeStamp) {
        MdcUtil.addEndTimeStampToMDC(endTimeStamp);
    }

    public static Timestamp getStartTimeStampFromMDC() {
        return MdcUtil.getStartTimeStampToMDC();
    }

    public static Timestamp getEndTimeStampFromMDC() {
        return MdcUtil.getEndTimeStampToMDC();
    }

    public static void flushMDC() {
        MdcUtil.flushMDC();
    }

}
