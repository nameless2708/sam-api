package com.sam.api.exception;

import io.swagger.annotations.ApiModel;

import java.util.Map;

@ApiModel
public class ErrorResponse {

    private final String message;
    private final Map<String, String> data;

    public ErrorResponse(String message, Map<String, String> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getData() {
        return data;
    }
}
