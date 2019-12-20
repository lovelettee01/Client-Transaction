package com.kakaopay.task.exception;

import com.kakaopay.task.exception.dto.ErrorCode;
import lombok.Getter;

/**
 * Rest API관련 Runtime에러를 처리하기위한 Exception Class
 */
@Getter
public class ApiException extends RuntimeException {

    private ErrorCode errorCode;
    public ApiException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
