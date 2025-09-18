package com.sanitas.recuerdame.shared.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_FOUND;
    }
}
