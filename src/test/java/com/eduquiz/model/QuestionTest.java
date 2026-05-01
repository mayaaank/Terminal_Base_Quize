package com.eduquiz.model;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Question class.
 */
class QuestionTest {

    @Test
    void testIsCorrect_shouldReturnTrueForCorrectOption() {
        List<Option> options = List.of(
                new Option(1, "A"),
                new Option(2, "B"),
                new Option(3, "C"),
                new Option(4, "D")
        );
        Question q = new Question("Q?", options, 2, "B is correct");
        assertTrue(q.isCorrect(2));
    }

    @Test
    void testIsCorrect_shouldReturnFalseForWrongOption() {
        List<Option> options = List.of(
                new Option(1, "A"),
                new Option(2, "B"),
                new Option(3, "C"),
                new Option(4, "D")
        );
        Question q = new Question("Q?", options, 2, "B is correct");
        assertFalse(q.isCorrect(1));
        assertFalse(q.isCorrect(3));
        assertFalse(q.isCorrect(4));
    }

    @Test
    void testConstructor_shouldRejectNullQuestionText() {
        List<Option> options = List.of(
                new Option(1, "A"),
                new Option(2, "B"),
                new Option(3, "C"),
                new Option(4, "D")
        );
        assertThrows(IllegalArgumentException.class,
                () -> new Question(null, options, 1, "exp"));
    }

    @Test
    void testConstructor_shouldRequireExactlyFourOptions() {
        List<Option> options = List.of(new Option(1, "A"));
        assertThrows(IllegalArgumentException.class,
                () -> new Question("Q?", options, 1, "exp"));
    }

    @Test
    void testConstructor_shouldRejectInvalidCorrectOptionId() {
        List<Option> options = List.of(
                new Option(1, "A"),
                new Option(2, "B"),
                new Option(3, "C"),
                new Option(4, "D")
        );
        assertThrows(IllegalArgumentException.class,
                () -> new Question("Q?", options, 5, "exp"));
    }
}
