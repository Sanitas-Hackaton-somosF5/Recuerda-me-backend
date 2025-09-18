package com.sanitas.recuerdame.shared.exceptions;

public class MedicationAlreadyExistsException extends RuntimeException {
  public MedicationAlreadyExistsException(String message) {
    super(message);
  }

  public ErrorCode getErrorCode() {
    return ErrorCode.BAD_REQUEST;
  }
}
