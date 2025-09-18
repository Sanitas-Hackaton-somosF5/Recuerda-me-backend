package com.sanitas.recuerdame.shared.exceptions;

public class MedicationNotFoundException extends RuntimeException {
    public MedicationNotFoundException(String message) {
        super(message);
    }

    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_FOUND;
    }
}
