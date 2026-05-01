package com.eduquiz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages a quiz session lifecycle with multiple student attempts.
 * Single Responsibility: handles session state and attempt tracking.
 * Prevents God Class by separating session logic from Attempt and Quiz.
 */
public class QuizSession {
    private final Quiz quiz;
    private final List<Attempt> attempts;
    private SessionStatus status;

    /**
     * Constructs a new QuizSession for a quiz.
     *
     * @param quiz the quiz for this session
     */
    public QuizSession(Quiz quiz) {
        if (quiz == null) {
            throw new IllegalArgumentException("Quiz cannot be null");
        }
        this.quiz = quiz;
        this.attempts = new ArrayList<>();
        this.status = SessionStatus.NOT_STARTED;
    }

    /**
     * Starts the session (activates the quiz).
     */
    public void start() {
        this.status = SessionStatus.ACTIVE;
        quiz.setActive(true);
    }

    /**
     * Marks the session as completed.
     */
    public void complete() {
        this.status = SessionStatus.COMPLETED;
        quiz.setActive(false);
    }

    /**
     * Adds an attempt to this session.
     *
     * @param attempt the student's attempt
     */
    public void addAttempt(Attempt attempt) {
        if (attempt == null) {
            throw new IllegalArgumentException("Attempt cannot be null");
        }
        attempts.add(attempt);
    }

    public Quiz getQuiz() {
        return quiz;
    }

    /**
     * Returns unmodifiable view of attempts.
     */
    public List<Attempt> getAttempts() {
        return Collections.unmodifiableList(attempts);
    }

    public SessionStatus getStatus() {
        return status;
    }

    /**
     * Returns number of participants.
     */
    public int getParticipantCount() {
        return attempts.size();
    }
}
