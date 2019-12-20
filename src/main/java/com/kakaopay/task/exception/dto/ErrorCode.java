package com.kakaopay.task.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 공통에러 코드  
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
	NOT_FOUND		 		(404, "C000", "Not Found"				),
	INVALID_INPUT_VALUE		(400, "C001", "Invalid Input Value"		),
    METHOD_NOT_ALLOWED		(405, "C002", "Method Not Allowed"		),
    ENTITY_NOT_FOUND		(400, "C003", "Entity Not Found"		),
    INTERNAL_SERVER_ERROR	(500, "C004", "Internal Server Error"	),
    INVALID_TYPE_VALUE		(400, "C005", "Invalid Type Value"		),
    HANDLE_ACCESS_DENIED	(403, "C006", "Access is Denied"		);
	
	
    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status 	= status;
        this.message 	= message;
        this.code 		= code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }


}
