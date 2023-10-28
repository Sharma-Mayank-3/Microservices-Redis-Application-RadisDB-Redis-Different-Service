package com.userfeign.exception;

public class FeignNotFoundException extends RuntimeException{
    private String message;

    public FeignNotFoundException(String message) {
        super(String.format("%s ", message));
        this.message = message;
    }
}
