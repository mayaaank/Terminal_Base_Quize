package com.eduquiz.model;

/**
 * Enum representing the lifecycle status of a quiz session.
 * Type-safe state machine for session management.
 */
public enum SessionStatus {
    NOT_STARTED,
    ACTIVE,
    COMPLETED
}
