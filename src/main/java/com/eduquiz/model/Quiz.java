package com.eduquiz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a quiz containing multiple questions.
 * Composition: has-a ArrayList<Question>.
 * Demonstrates ArrayList usage — dynamic array that grows as questions are added.
 */
public class Quiz {
    private final String quizId;
    private final String title;
    private final String accessCode;
    private final ArrayList<Question> questions;  // dynamic array of questions
    private boolean isActive;

    /**
     * Constructs a Quiz with auto-generated ID and access code.
     *
     * @param title quiz title
     * @param accessCode unique access code for students to join
     */
    public Quiz(String quizId, String title, String accessCode) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Quiz title cannot be null or empty");
        }
        if (accessCode == null || accessCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Access code cannot be null or empty");
        }
        this.quizId = quizId;
        this.title = title.trim();
        this.accessCode = accessCode.trim();
        this.questions = new ArrayList<>();  // OOP: ArrayList is an object, not raw array
        this.isActive = false;
    }

    /**
     * Adds a question to this quiz.
     * Demonstrates dynamic growth: ArrayList grows automatically.
     *
     * @param question the question to add
     */
    public void addQuestion(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        questions.add(question);
    }

    public String getQuizId() {
        return quizId;
    }

    public String getTitle() {
        return title;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    /**
     * Returns unmodifiable view of questions.
     *
     * @return list of questions in insertion order
     */
    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /**
     * Returns a shuffled copy of questions for randomized quiz order.
     *
     * @return shuffled list of questions
     */
    public List<Question> getShuffledQuestions() {
        List<Question> shuffled = new ArrayList<>(questions);
        Collections.shuffle(shuffled);
        return shuffled;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return accessCode.equals(quiz.accessCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessCode);
    }
}
