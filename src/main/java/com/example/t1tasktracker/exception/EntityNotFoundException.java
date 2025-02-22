package com.example.t1tasktracker.exception;

public class EntityNotFoundException extends RuntimeException {
    EntityNotFoundException(String message) {
        super(message);
    }
}
