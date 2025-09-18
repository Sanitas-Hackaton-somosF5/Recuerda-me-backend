package com.sanitas.recuerdame.shared.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    private ErrorResponse buildErrorResponse(ErrorCode errorCode, String message, HttpStatus httpStatus, String path) {
        return new ErrorResponse(errorCode, message, httpStatus, path);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception, HttpServletRequest request) {

        ErrorResponse error = buildErrorResponse(
                exception.getErrorCode(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMedicationNotFoundException(MedicationNotFoundException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(
                exception.getErrorCode(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MedicationAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleMedicationAlreadyExists(MedicationAlreadyExistsException exception, HttpServletRequest request) {

        ErrorResponse error = buildErrorResponse(
                exception.getErrorCode(),
                exception.getMessage(),
                HttpStatus.CONFLICT,
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {

        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Entrada inválida");

        ErrorResponse error = buildErrorResponse(
                ErrorCode.VALIDATION_ERROR,
                message,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, HttpServletRequest request) {

        ErrorResponse error = buildErrorResponse(
                ErrorCode.SERVER_ERROR,
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
