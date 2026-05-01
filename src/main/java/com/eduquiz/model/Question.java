package com.eduquiz.model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a quiz question with options and correct answer.
 * Encapsulation: private fields, getters only (immutable after creation).
 * No setters — makes class thread-safe by design.
 */
public class Question {
    private final String questionText;
    private final List<Option> options;  // exactly 4 options
    private final int correctOptionId;   // 1-4
    private final String explanation;

    /**
     * Constructs a Question with validation.
     *
     * @param questionText the question text
     * @param options list of exactly 4 options
     * @param correctOptionId the correct option ID (1-4)
     * @param explanation explanation for the correct answer
     * @throws IllegalArgumentException if validation fails
     */
    public Question(String questionText, List<Option> options, int correctOptionId, String explanation) {
        if (questionText == null || questionText.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty");
        }
        if (options == null || options.size() != 4) {
            throw new IllegalArgumentException("Question must have exactly 4 options");
        }
        boolean validIds = options.stream()
                .allMatch(o -> o.getOptionId() >= 1 && o.getOptionId() <= 4);
        if (!validIds) {
            throw new IllegalArgumentException("All option IDs must be between 1 and 4");
        }
        if (correctOptionId < 1 || correctOptionId > 4) {
            throw new IllegalArgumentException("Correct option ID must be between 1 and 4");
        }
        // Verify correct option exists in options list
        boolean found = options.stream()
                .anyMatch(o -> o.getOptionId() == correctOptionId);
        if (!found) {
            throw new IllegalArgumentException("Correct option must be present in options list");
        }

        this.questionText = questionText.trim();
        this.options = new ArrayList<>(options);  // defensive copy
        this.correctOptionId = correctOptionId;
        this.explanation = explanation != null ? explanation.trim() : "";
    }

    /**
     * Checks if the chosen option ID is correct.
     *
     * @param chosenId the option ID selected by user
     * @return true if chosenId equals correctOptionId
     */
    public boolean isCorrect(int chosenId) {
        return chosenId == correctOptionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    /**
     * Returns an unmodifiable view of options.
     */
    public List<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

    public int getCorrectOptionId() {
        return correctOptionId;
    }

    public String getExplanation() {
        return explanation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return correctOptionId == question.correctOptionId &&
                questionText.equals(question.questionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionText, correctOptionId);
    }
}
