package com.example.bonus.exception;

import lombok.Builder;
import lombok.Data;

// 에러발생시 던져줄 모델
@Builder
@Data
public class ErrorResponse {
    private int code;
    private String message;
}
