package com.eduquiz.service;

import com.eduquiz.exception.InvalidAccessCodeException;
import com.eduquiz.model.*;
import com.eduquiz.util.CodeGenerator;

import java.util.*;

/**
 * Implementation of QuizService.
 * Manages all quizzes, sessions, and question operations.
 * Demonstrates HashMap usage for O(1) lookup by access code.
 */
public class QuizManager implements QuizService {
    private final Map<String, Quiz> quizMap;          // accessCode -> Quiz
    private final Map<String, QuizSession> sessionMap; // accessCode -> Session
    private final Set<String> usedCodes;             // for collision detection

    /**
     * Constructs an empty QuizManager.
     */
    public QuizManager() {
        this.quizMap = new HashMap<>();
        this.sessionMap = new HashMap<>();
        this.usedCodes = new HashSet<>();
    }

    @Override
    public Quiz createQuiz(String title) {
        String code = CodeGenerator.generate(usedCodes);
        usedCodes.add(code);
        String quizId = UUID.randomUUID().toString();
        Quiz quiz = new Quiz(quizId, title, code);
        quizMap.put(code, quiz);
        return quiz;
    }

    @Override
    public void addQuestion(String accessCode, Question question) throws InvalidAccessCodeException {
        Quiz quiz = getQuizByCode(accessCode);
        quiz.addQuestion(question);
    }

    @Override
    public Quiz getQuizByCode(String code) throws InvalidAccessCodeException {
        if (!quizMap.containsKey(code)) {
            throw new InvalidAccessCodeException("No quiz found for code: " + code);
        }
        return quizMap.get(code);
    }

    @Override
    public void startSession(String code) throws InvalidAccessCodeException {
        Quiz quiz = getQuizByCode(code);
        if (quiz.getQuestionCount() == 0) {
            throw new IllegalStateException("Cannot start quiz with zero questions");
        }
        QuizSession session = new QuizSession(quiz);
        session.start();
        sessionMap.put(code, session);
    }

    /**
     * Gets the session for a quiz code.
     *
     * @param code access code
     * @return QuizSession or null if not started
     */
    public QuizSession getSession(String code) {
        return sessionMap.get(code);
    }

    /**
     * Adds an attempt to a quiz session.
     *
     * @param code access code
     * @param attempt student attempt
     */
    public void recordAttempt(String code, Attempt attempt) {
        QuizSession session = sessionMap.get(code);
        if (session != null) {
            session.addAttempt(attempt);
        }
    }

    /**
     * Gets all quizzes.
     *
     * @return collection of quizzes
     */
    public Collection<Quiz> getAllQuizzes() {
        return Collections.unmodifiableCollection(quizMap.values());
    }

    /**
     * Gets all sessions.
     *
     * @return collection of sessions
     */
    public Collection<QuizSession> getAllSessions() {
        return Collections.unmodifiableCollection(sessionMap.values());
    }
}
