package com.eduquiz.exception;

/**
 * Custom checked exception thrown when an invalid access code is provided.
 * Demonstrates exception handling as a first-class OOP construct.
 */
public class InvalidAccessCodeException extends Exception {
    /**
     * Constructs exception with detail message.
     *
     * @param message error description
     */
    public InvalidAccessCodeException(String message) {
        super(message);
    }
}
