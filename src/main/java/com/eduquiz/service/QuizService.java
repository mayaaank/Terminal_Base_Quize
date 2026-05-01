package com.eduquiz.service;

import com.eduquiz.exception.InvalidAccessCodeException;
import com.eduquiz.model.Quiz;

/**
 * Service interface for quiz management operations.
 * Abstraction layer that defines the contract for quiz operations.
 */
public interface QuizService {
    /**
     * Creates a new quiz and returns it.
     *
     * @param title quiz title
     * @return created Quiz object
     */
    Quiz createQuiz(String title);

    /**
     * Adds a question to a quiz identified by access code.
     *
     * @param accessCode quiz access code
     * @param question question to add
     * @throws InvalidAccessCodeException if no quiz found for code
     */
    void addQuestion(String accessCode, com.eduquiz.model.Question question)
            throws InvalidAccessCodeException;

    /**
     * Retrieves a quiz by access code.
     *
     * @param code access code
     * @return Quiz object
     * @throws InvalidAccessCodeException if quiz not found
     */
    Quiz getQuizByCode(String code) throws InvalidAccessCodeException;

    /**
     * Starts a session for the quiz with given code.
     *
     * @param code access code
     * @throws InvalidAccessCodeException if quiz not found
     */
    void startSession(String code) throws InvalidAccessCodeException;
}
