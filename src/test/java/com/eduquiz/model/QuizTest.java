package com.eduquiz.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Quiz class.
 */
class QuizTest {

    @Test
    void testAddQuestion_increasesCount() {
        Quiz quiz = new Quiz("q1", "Math", "QZ-1234");
        assertEquals(0, quiz.getQuestionCount());

        Question q = new Question("1+1?", List.of(
                new Option(1, "1"), new Option(2, "2"),
                new Option(3, "3"), new Option(4, "4")
        ), 2, "2");
        quiz.addQuestion(q);
        assertEquals(1, quiz.getQuestionCount());

        quiz.addQuestion(q);
        assertEquals(2, quiz.getQuestionCount());
    }

    @Test
    void testGetQuestions_returnsUnmodifiableList() {
        Quiz quiz = new Quiz("q1", "Math", "QZ-1234");
        Question q = new Question("1+1?", List.of(
                new Option(1, "1"), new Option(2, "2"),
                new Option(3, "3"), new Option(4, "4")
        ), 2, "2");
        quiz.addQuestion(q);

        List<Question> questions = quiz.getQuestions();
        assertThrows(UnsupportedOperationException.class, () -> questions.add(q));
    }

    @Test
    void testConstructor_shouldRejectNullTitle() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quiz("id", null, "QZ-1234"));
    }

    @Test
    void testGetShuffledQuestions_returnsSameSize() {
        Quiz quiz = new Quiz("q1", "Math", "QZ-1234");
        for (int i = 1; i <= 5; i++) {
            quiz.addQuestion(new Question("Q" + i, List.of(
                    new Option(1, "A"), new Option(2, "B"),
                    new Option(3, "C"), new Option(4, "D")
            ), 1, "A"));
        }
        List<Question> shuffled = quiz.getShuffledQuestions();
        assertEquals(5, shuffled.size());
    }
}
