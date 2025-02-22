package com.example.t1tasktracker.exception;

import java.time.Instant;

public record ErrorResponse(Integer statusCode, String message, Instant timestamp) {
}
