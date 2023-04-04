package com.example.demoproject.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {
    private final boolean success;
    private final boolean failure;
    private final String message;
    private String error;


    public String getError() {
        return error;
    }
}
