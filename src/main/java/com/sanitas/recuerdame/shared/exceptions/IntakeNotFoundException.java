package com.sanitas.recuerdame.shared.exceptions;

import lombok.Getter;

@Getter
public class IntakeNotFoundException extends RuntimeException {
  private final ErrorCode errorCode;

  public IntakeNotFoundException(String message) {
    super(message);
    this.errorCode = ErrorCode.NOT_FOUND;
  }
}
