package com.eduquiz.service;

import com.eduquiz.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Scoreboard class.
 */
class ScoreboardTest {

    @Test
    void testRank_sortsByScoreDescending() {
        Scoreboard scoreboard = new Scoreboard();
        Student s1 = new Student("s1", "Alice");
        Student s2 = new Student("s2", "Bob");

        Quiz quiz1 = new Quiz("q1", "Quiz", "QZ-0001");
        quiz1.addQuestion(new Question("Q?", List.of(
                new Option(1, "A"), new Option(2, "B"),
                new Option(3, "C"), new Option(4, "D")
        ), 1, "A"));

        Attempt a1 = new Attempt(s1, quiz1);
        a1.start();
        a1.recordAnswer(quiz1.getQuestions().get(0), 1);
        a1.finalise();

        Attempt a2 = new Attempt(s2, quiz1);
        a2.start();
        a2.recordAnswer(quiz1.getQuestions().get(0), 2);  // wrong
        a2.finalise();

        List<Attempt> attempts = List.of(a2, a1);  // a2 first (lower score)
        List<Attempt> ranked = scoreboard.rank(attempts);

        assertEquals(a1, ranked.get(0));  // higher score first
        assertEquals(a2, ranked.get(1));
    }

    @Test
    void testRank_tieBreakerUsesTimeAscending() {
        Scoreboard scoreboard = new Scoreboard();
        Student s1 = new Student("s1", "Alice");
        Student s2 = new Student("s2", "Bob");

        Quiz quiz1 = new Quiz("q1", "Quiz", "QZ-0001");
        quiz1.addQuestion(new Question("Q?", List.of(
                new Option(1, "A"), new Option(2, "B"),
                new Option(3, "C"), new Option(4, "D")
        ), 1, "A"));

        Attempt a1 = new Attempt(s1, quiz1);
        a1.start();
        // simulate slow time
        try { Thread.sleep(50); } catch (InterruptedException ignored) {}
        a1.recordAnswer(quiz1.getQuestions().get(0), 1);
        a1.finalise();

        Attempt a2 = new Attempt(s2, quiz1);
        a2.start();
        // simulate fast time
        try { Thread.sleep(10); } catch (InterruptedException ignored) {}
        a2.recordAnswer(quiz1.getQuestions().get(0), 1);
        a2.finalise();

        List<Attempt> attempts = List.of(a1, a2);
        List<Attempt> ranked = scoreboard.rank(attempts);

        // a2 should rank higher (faster time)
        assertEquals(a2, ranked.get(0));
        assertEquals(a1, ranked.get(1));
    }

    @Test
    void testRank_emptyList_returnsEmptyList() {
        Scoreboard scoreboard = new Scoreboard();
        List<Attempt> ranked = scoreboard.rank(List.of());
        assertTrue(ranked.isEmpty());
    }
}
