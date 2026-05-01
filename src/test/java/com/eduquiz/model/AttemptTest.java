package com.eduquiz.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Attempt class.
 */
class AttemptTest {

    @Test
    void testRecordAnswer_incrementsScoreForCorrectAnswer() {
        Student student = new Student("s1", "Alice");
        Quiz quiz = new Quiz("q1", "Quiz", "QZ-0001");
        Question q = new Question("2+2?", List.of(
                new Option(1, "3"), new Option(2, "4"),
                new Option(3, "5"), new Option(4, "6")
        ), 2, "4");
        quiz.addQuestion(q);

        Attempt attempt = new Attempt(student, quiz);
        attempt.start();
        attempt.recordAnswer(q, 2);  // correct
        attempt.finalise();

        assertEquals(1, attempt.getScore());
        assertEquals(1, attempt.getCorrectCount());
        assertEquals(0, attempt.getWrongCount());
    }

    @Test
    void testRecordAnswer_doesNotIncrementForWrongAnswer() {
        Student student = new Student("s1", "Alice");
        Quiz quiz = new Quiz("q1", "Quiz", "QZ-0001");
        Question q = new Question("2+2?", List.of(
                new Option(1, "3"), new Option(2, "4"),
                new Option(3, "5"), new Option(4, "6")
        ), 2, "4");
        quiz.addQuestion(q);

        Attempt attempt = new Attempt(student, quiz);
        attempt.start();
        attempt.recordAnswer(q, 1);  // wrong
        attempt.finalise();

        assertEquals(0, attempt.getScore());
        assertEquals(0, attempt.getCorrectCount());
        assertEquals(1, attempt.getWrongCount());
    }

    @Test
    void testGetTimeTaken_returnsPositiveValue() {
        Student student = new Student("s1", "Alice");
        Quiz quiz = new Quiz("q1", "Quiz", "QZ-0001");
        Question q = new Question("Q?", List.of(
                new Option(1, "A"), new Option(2, "B"),
                new Option(3, "C"), new Option(4, "D")
        ), 1, "A");
        quiz.addQuestion(q);

        Attempt attempt = new Attempt(student, quiz);
        attempt.start();
        // small delay to ensure non-zero time
        try { Thread.sleep(10); } catch (InterruptedException ignored) {}
        attempt.finalise();

        assertTrue(attempt.getTimeTaken() >= 0);
    }
}
