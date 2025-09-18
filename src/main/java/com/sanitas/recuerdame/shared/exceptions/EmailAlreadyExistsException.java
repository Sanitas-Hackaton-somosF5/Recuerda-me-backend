package com.sanitas.recuerdame.shared.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return ErrorCode.EMAIL_ALREADY_EXISTS;
    }
}