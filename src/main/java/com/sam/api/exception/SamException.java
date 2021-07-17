package com.sam.api.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class SamException extends RuntimeException {
    private HttpStatus httpStatus;
    private Map<String, String> data;

    public SamException(String message, HttpStatus httpStatus, Map<String, String> data) {
        super(message);
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public SamException(HttpStatus httpStatus, Map<String, String> data) {
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public SamException() {
    }

    public ErrorResponse toResponse() {
        return new ErrorResponse(this.getMessage(), this.data);
    }
}
