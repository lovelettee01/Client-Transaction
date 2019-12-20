package com.kakaopay.task.exception.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.kakaopay.task.exception.ApiException;

/**
 * Response Body에 입력될 Error Layout 
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String code;				//에러코드		(ErrorCode에 정의된 코드)
    private String message;				//에러메시지	(ErrorCode에 정의된 메시지)
    private String reason;				//에러 이유	(사용자 입력값 or Exception Message)
    private int status;					//에러상태코드
    private List<FieldError> errors;

    
    private ErrorResponse(final ErrorCode code) {
    	this(code, "", new ArrayList<>());
    }
    
    private ErrorResponse(final ErrorCode code, String reason) {
    	this(code, reason, new ArrayList<>());
    }
    
    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
    	this(code, "", errors);
    }

    private ErrorResponse(final ErrorCode code,final String reason, final List<FieldError> errors) {
    	this.code 		= code.getCode();
    	this.message 	= code.getMessage();
        this.status 	= code.getStatus();
        this.reason		= reason;
        this.errors 	= errors;
    }


    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }
    
    public static ErrorResponse of(ApiException e) {
    	final ErrorCode errorCode = e.getErrorCode();
        final String meaasge = e.getMessage() == null ? "" : e.getMessage();
        return new ErrorResponse(errorCode, meaasge);
    }
    
    public static ErrorResponse of(Exception e) {
    	final ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    	final String meaasge = e.getMessage() == null ? "" : e.getMessage();
        return new ErrorResponse(errorCode, meaasge);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }


}
