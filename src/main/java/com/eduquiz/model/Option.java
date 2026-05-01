package com.eduquiz.model;

import java.util.Objects;

/**
 * Represents a single answer option for a question.
 * Encapsulation: optionId and text are immutable after construction.
 */
public class Option {
    private final int optionId;   // 1-4
    private final String text;

    /**
     * Constructs an Option with given ID and text.
     *
     * @param optionId the option number (1-4)
     * @param text the option text
     * @throws IllegalArgumentException if optionId invalid or text is null/empty
     */
    public Option(int optionId, String text) {
        if (optionId < 1 || optionId > 4) {
            throw new IllegalArgumentException("Option ID must be between 1 and 4");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Option text cannot be null or empty");
        }
        this.optionId = optionId;
        this.text = text.trim();
    }

    public int getOptionId() {
        return optionId;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return optionId == option.optionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionId);
    }
}
