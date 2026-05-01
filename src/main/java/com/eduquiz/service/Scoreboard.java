package com.eduquiz.service;

import com.eduquiz.model.Attempt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Ranks quiz attempts based on score and time.
 * Demonstrates Polymorphism: Comparator is a functional interface with custom ordering.
 */
public class Scoreboard {

    /**
     * Comparator that defines ranking order:
     * Primary: higher score first (descending)
     * Secondary: shorter time first (ascending)
     * Polymorphism: same Comparator interface, different compare() logic
     */
    private static final Comparator<Attempt> RANK_ORDER =
            Comparator.comparingInt(Attempt::getScore).reversed()
                      .thenComparingLong(Attempt::getTimeTaken);

    /**
     * Ranks a list of attempts.
     *
     * @param attempts list of attempts to rank
     * @return sorted list (highest rank first)
     */
    public List<Attempt> rank(List<Attempt> attempts) {
        List<Attempt> sorted = new ArrayList<>(attempts);
        sorted.sort(RANK_ORDER);
        return sorted;
    }
}
