package com.example.t1tasktracker.exception;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
