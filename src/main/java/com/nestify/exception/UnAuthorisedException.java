package com.nestify.exception;

public class UnAuthorisedException extends RuntimeException {
    public UnAuthorisedException(String message) { super(message); }
}
