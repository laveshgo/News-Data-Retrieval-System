package com.lavesh.common.core.util;

import com.lavesh.common.core.constant.AppConstant;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.MDC;

import java.sql.Timestamp;

public class MdcUtil {

    public static void addTransactionIdToMdc(final String requestId) {
        MDC.put(AppConstant.REQUEST_LOG_ID, requestId);
    }

    public static String getTransactionIdFromMdc() {
        return MDC.get(AppConstant.REQUEST_LOG_ID);
    }


    public static void addCustomerIdToMDC(final String customerId) {
        MDC.put(AppConstant.REQUEST_CUSTOMER_ID, customerId);
    }


    public static void addHostNameToMDC(final String hostName) {
        MDC.put(AppConstant.REQUEST_HOST_NAME, hostName);
    }

    public static void addMethodNameToMDC(final String methodName) {
        MDC.put(AppConstant.REQUEST_METHOD_NAME, methodName);
    }

    public static void addStartTimeStampToMDC(final Timestamp startTimeStamp) {
        if (ObjectUtils.isEmpty(startTimeStamp)) {
            return;
        }

        MDC.put(AppConstant.REQUEST_START_TIMESTAMP, startTimeStamp.toString());
    }

    public static Timestamp getStartTimeStampToMDC() {
        String timeStamp = MDC.get(AppConstant.REQUEST_START_TIMESTAMP);
        if (ObjectUtils.isEmpty(timeStamp)) {
            return null;
        }

        return Timestamp.valueOf(timeStamp);
    }

    public static void addEndTimeStampToMDC(final Timestamp endTimeStamp) {
        if (ObjectUtils.isEmpty(endTimeStamp)) {
            return;
        }

        MDC.put(AppConstant.REQUEST_END_TIMESTAMP, endTimeStamp.toString());
    }

    public static Timestamp getEndTimeStampToMDC() {
        String timeStamp = MDC.get(AppConstant.REQUEST_END_TIMESTAMP);
        if (ObjectUtils.isEmpty(timeStamp)) {
            return null;
        }

        return Timestamp.valueOf(timeStamp);
    }

    public static void flushMDC() {
        MDC.remove(AppConstant.REQUEST_CUSTOMER_ID);
        MDC.remove(AppConstant.REQUEST_LOG_ID);
        MDC.remove(AppConstant.REQUEST_HOST_NAME);
        MDC.remove(AppConstant.REQUEST_METHOD_NAME);
        MDC.remove(AppConstant.REQUEST_START_TIMESTAMP);
        MDC.remove(AppConstant.REQUEST_END_TIMESTAMP);

    }
}
