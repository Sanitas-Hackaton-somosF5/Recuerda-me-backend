package com.sanitas.recuerdame.shared.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

public class GlobalExceptionHandlerTest {
  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  void handleIntakeNotFound_shouldReturnErrorResponse() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/api/intakes/99");
    IntakeNotFoundException exception = new IntakeNotFoundException("Intake with id 99 not found");

    ResponseEntity<ErrorResponse> response = handler.handleIntakeNotFound(exception, request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND.name());
    assertThat(response.getBody().getMessage()).isEqualTo("Intake with id 99 not found");
    assertThat(response.getBody().getPath()).isEqualTo("/api/intakes/99");
  }
}
