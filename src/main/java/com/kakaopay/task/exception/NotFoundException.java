package com.kakaopay.task.exception;

import com.kakaopay.task.exception.dto.ErrorCode;

/**
 * API 입출력 값이 존재하지 않는경우 
 */
public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND);
    }
}
