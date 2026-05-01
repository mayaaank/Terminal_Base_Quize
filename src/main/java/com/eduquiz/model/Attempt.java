package com.eduquiz.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single student's attempt at a quiz.
 * Tracks answers, score, and timing information.
 */
public class Attempt {
    private final Student student;
    private final Quiz quiz;
    private final Map<Question, Integer> answers;  // Question -> chosenOptionId
    private int score;
    private long startTime;
    private long endTime;

    /**
     * Constructs an Attempt for a student taking a quiz.
     *
     * @param student the student
     * @param quiz the quiz being attempted
     */
    public Attempt(Student student, Quiz quiz) {
        if (student == null || quiz == null) {
            throw new IllegalArgumentException("Student and Quiz cannot be null");
        }
        this.student = student;
        this.quiz = quiz;
        this.answers = new HashMap<>();
        this.score = 0;
    }

    /**
     * Records an answer for a question.
     * If correct, increments score.
     *
     * @param question the question being answered
     * @param chosenOptionId the option ID chosen by student (1-4)
     */
    public void recordAnswer(Question question, int chosenOptionId) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        answers.put(question, chosenOptionId);
        if (question.isCorrect(chosenOptionId)) {
            score++;
        }
    }

    /**
     * Starts the attempt timer.
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Stops the attempt timer.
     */
    public void finalise() {
        this.endTime = System.currentTimeMillis();
    }

    public Student getStudent() {
        return student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public int getScore() {
        return score;
    }

    public long getTimeTaken() {
        if (endTime == 0 || startTime == 0) {
            return 0;
        }
        return endTime - startTime;
    }

    public Map<Question, Integer> getAnswers() {
        return new HashMap<>(answers);  // defensive copy
    }

    /**
     * Returns the number of correct answers.
     */
    public int getCorrectCount() {
        return score;
    }

    /**
     * Returns the number of wrong answers.
     */
    public int getWrongCount() {
        return answers.size() - score;
    }
}
