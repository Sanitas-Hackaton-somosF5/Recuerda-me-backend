package com.sanitas.recuerdame.shared.exceptions;

public class emailAlreadyExistsException extends RuntimeException {
    public emailAlreadyExistsException(String message) {
        super(message);
    }
}