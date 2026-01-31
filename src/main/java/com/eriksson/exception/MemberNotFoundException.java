package com.eriksson.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException (String message) {
        super(message);
    }
}
