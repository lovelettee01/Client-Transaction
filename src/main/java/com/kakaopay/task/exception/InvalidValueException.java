package com.kakaopay.task.exception;

import com.kakaopay.task.exception.dto.ErrorCode;

/**
 * API 입력값 오류 처리
 */
public class InvalidValueException extends ApiException {
    public InvalidValueException(String value) {
        super(value, ErrorCode.INVALID_INPUT_VALUE);
    }
}
