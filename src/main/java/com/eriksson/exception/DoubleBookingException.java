package com.eriksson.exception;

public class DoubleBookingException extends RuntimeException{
    //ogiltiga datum, saknad medlem, dubbelbokning
    public DoubleBookingException(String message) {
        super(message);
    }
}
