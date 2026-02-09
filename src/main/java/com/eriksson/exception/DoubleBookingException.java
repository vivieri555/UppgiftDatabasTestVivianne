package com.eriksson.exception;

public class DoubleBookingException extends RuntimeException{
    public DoubleBookingException(String message) {
        super(message);
    }
}
