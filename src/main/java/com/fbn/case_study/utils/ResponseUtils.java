package com.fbn.case_study.utils;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ResponseUtils {
    public static <T> ApiResponse<T> createSuccessResponse(T data, String message, int statusCode) {
        var requestId = UUID.randomUUID().toString();
        var time  = LocalDateTime.now();

        MDC.put("requestId",requestId );
        MDC.put("requestTime", time.toString());
        MDC.put("data", data.toString());
        MDC.put("message", message);
        return ApiResponse.<T>builder()
                .requestTime(time)
                .requestType("Outbound")
                .referenceId(requestId)
                .status(true)
                .message(message)
                .statusCode(statusCode)
                .data(data)
                .build();
    }
    public static <T> ApiResponse<T> createSuccessResponse(T data, String message) {
        var requestId = UUID.randomUUID().toString();
        var time  = LocalDateTime.now();

        MDC.put("requestId",requestId );
        MDC.put("requestTime", time.toString());
        MDC.put("data", data.toString());
        MDC.put("message", message);
        return ApiResponse.<T>builder()
                .requestTime(time)
                .requestType("Outbound")
                .referenceId(requestId)
                .status(true)
                .message(message)
                .data(data)
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    public static <T> ApiResponse<T> createFailureResponse(String error, String message) {
        var requestId = UUID.randomUUID().toString();
        var time  = LocalDateTime.now();

        MDC.put("requestId",requestId );
        MDC.put("requestTime", time.toString());
        MDC.put("error", error);
        MDC.put("message", message);
        return ApiResponse.<T>builder()
                .requestTime(time)
                .requestType("Outbound")
                .referenceId(requestId)
                .status(false)
                .message(message)
                .error(error)
                .build();
    }

}
