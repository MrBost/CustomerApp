package com.fbn.case_study.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class ApiResponse<T>{
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestTime = LocalDateTime.now();
    private String requestType;
    private String referenceId = UUID.randomUUID().toString();
    private boolean status;
    private String message;
    private String error_description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    private int statusCode;

    public ApiResponse(LocalDateTime requestTime, String requestType, String referenceId, String message, T data) {
        this.requestTime = requestTime;
        this.requestType = requestType;
        this.referenceId = referenceId;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

}

