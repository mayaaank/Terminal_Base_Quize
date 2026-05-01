package com.eduquiz.service;

import com.eduquiz.exception.InvalidAccessCodeException;
import com.eduquiz.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for QuizManager.
 */
class QuizManagerTest {

    @Test
    void testCreateQuiz_returnsQuizWithUniqueCode() {
        QuizManager manager = new QuizManager();
        Quiz quiz = manager.createQuiz("Sample Quiz");

        assertNotNull(quiz);
        assertNotNull(quiz.getAccessCode());
        assertTrue(quiz.getAccessCode().startsWith("QZ-"));
    }

    @Test
    void testGetQuizByCode_validCode_returnsQuiz() throws InvalidAccessCodeException {
        QuizManager manager = new QuizManager();
        Quiz quiz = manager.createQuiz("Test");

        Quiz retrieved = manager.getQuizByCode(quiz.getAccessCode());
        assertEquals(quiz.getTitle(), retrieved.getTitle());
    }

    @Test
    void testGetQuizByCode_invalidCode_throwsException() {
        QuizManager manager = new QuizManager();
        assertThrows(InvalidAccessCodeException.class,
                () -> manager.getQuizByCode("QZ-XXXX"));
    }

    @Test
    void testAddQuestion_linksQuestionToQuiz() throws InvalidAccessCodeException {
        QuizManager manager = new QuizManager();
        Quiz quiz = manager.createQuiz("Test");

        Question q = new Question("Q?", List.of(
                new Option(1, "A"), new Option(2, "B"),
                new Option(3, "C"), new Option(4, "D")
        ), 1, "A");

        manager.addQuestion(quiz.getAccessCode(), q);
        Quiz retrieved = manager.getQuizByCode(quiz.getAccessCode());
        assertEquals(1, retrieved.getQuestionCount());
    }
}
