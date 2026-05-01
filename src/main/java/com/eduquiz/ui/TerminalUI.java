package com.eduquiz.ui;

import com.eduquiz.exception.InvalidAccessCodeException;
import com.eduquiz.model.*;
import com.eduquiz.service.*;
import com.eduquiz.util.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Terminal User Interface — handles ALL user interaction.
 * NO business logic; delegates to service layer.
 * Demonstrates separation of concerns: UI vs business logic.
 */
public class TerminalUI {
    private final Scanner scanner;
    private final QuizManager quizManager;
    private final Scoreboard scoreboard;

    public TerminalUI() {
        this.scanner = new Scanner(System.in);
        this.quizManager = new QuizManager();
        this.scoreboard = new Scoreboard();
    }

    /**
     * Main entry point for the UI — shows main menu loop.
     */
    public void start() {
        ConsoleHelper.clearScreen();
        printBanner();
        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = InputValidator.readMenuInput(scanner, "\nEnter choice: ");
            switch (choice) {
                case "1" -> adminFlow();
                case "2" -> studentFlow();
                case "3" -> showLeaderboardFlow();
                case "4" -> conceptExplorer();
                case "5" -> {
                    System.out.println("\nThank you for using EduQuiz!");
                    running = false;
                }
                case "i" -> conceptExplorer();
                default -> ConsoleHelper.printError("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private void printBanner() {
        System.out.println();
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ╔══════════════════════════════════════════════════════════════════╗" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║                                                                  ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║    ███████╗██████╗ ██╗   ██╗ ██████╗ ██╗   ██╗██╗███████╗      ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║    ██╔════╝██╔══██╗██║   ██║██╔═══██╗██║   ██║██║╚══███╔╝      ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║    █████╗  ██║  ██║██║   ██║██║   ██║██║   ██║██║  ███╔╝       ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║    ██╔══╝  ██║  ██║██║   ██║██║▄▄ ██║██║   ██║██║ ███╔╝        ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║    ███████╗██████╔╝╚██████╔╝╚██████╔╝╚██████╔╝██║███████╗      ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║    ╚══════╝╚═════╝  ╚═════╝  ╚══▀▀═╝  ╚═════╝ ╚═╝╚══════╝     ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║                                                                  ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║       OOP-Based Online Quiz Management System — Full PRD        ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ║       Java Console Application | Academic Mini Project          ║" + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD +
                "  ╚══════════════════════════════════════════════════════════════════╝" + ConsoleHelper.RESET);
        System.out.println();
    }

    private void showMainMenu() {
        ConsoleHelper.printHeading("EDUQUIZ — MAIN MENU");
        System.out.println();
        System.out.println("  [1]  Host a New Quiz        (Admin)");
        System.out.println("  [2]  Join an Active Quiz    (Student)");
        System.out.println("  [3]  View Leaderboard");
        System.out.println("  [4]  Learn OOP Concepts     [i]");
        System.out.println("  [5]  Exit");
        System.out.println();
    }

    // ==================== ADMIN FLOW ====================

    private void adminFlow() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("ADMIN PANEL");
        String quizTitle = InputValidator.readString(scanner, "Enter quiz title: ");
        Quiz quiz = quizManager.createQuiz(quizTitle);
        ConsoleHelper.printSuccess("Quiz created!");
        ConsoleHelper.printSeparator(40);
        System.out.println("Access Code: " + ConsoleHelper.CYAN + quiz.getAccessCode() + ConsoleHelper.RESET);
        System.out.println("Share this code with your students.");
        ConsoleHelper.printSeparator(40);
        scanner.nextLine();  // consume newline

        boolean adding = true;
        while (adding) {
            showAddQuestionMenu(quiz);
            String choice = InputValidator.readMenuInput(scanner, "\nEnter choice: ");
            switch (choice) {
                case "1" -> addQuestionFlow(quiz);
                case "2" -> {
                    if (quiz.getQuestionCount() == 0) {
                        ConsoleHelper.printError("Cannot activate quiz with zero questions!");
                    } else {
                        try {
                            quizManager.startSession(quiz.getAccessCode());
                            ConsoleHelper.printSuccess("Quiz activated! Code: " + quiz.getAccessCode());
                        } catch (Exception e) {
                            ConsoleHelper.printError(e.getMessage());
                        }
                        adding = false;
                        scanner.nextLine();
                    }
                }
                case "i" -> {
                    conceptExplorer();
                    ConsoleHelper.clearScreen();
                    showAddQuestionMenu(quiz);
                }
                case "b" -> adding = false;
                default -> ConsoleHelper.printError("Invalid choice.");
            }
        }
        ConsoleHelper.clearScreen();
    }

    private void showAddQuestionMenu(Quiz quiz) {
        ConsoleHelper.printHeading("ADD QUESTION — " + quiz.getTitle().toUpperCase());
        System.out.println();
        System.out.println("Current questions in ArrayList: " + ConsoleHelper.GREEN + quiz.getQuestionCount() + ConsoleHelper.RESET);
        System.out.println();
        System.out.println("  [1] Add a Question");
        System.out.println("  [2] Activate & Finish");
        System.out.println("  [i] Learn about ArrayList");
        System.out.println("  [b] Back to Main Menu");
    }

    private void addQuestionFlow(Quiz quiz) {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("ADD QUESTION #" + (quiz.getQuestionCount() + 1));

        String qText = InputValidator.readString(scanner, "\nQuestion text: ");

        System.out.println("\nEnter 4 options:");
        List<Option> options = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            String optText = InputValidator.readString(scanner, "  Option " + i + ": ");
            options.add(new Option(i, optText));
        }

        int correct = InputValidator.readInt(scanner, 1, 4,
                "\nCorrect option (1–4): ");

        System.out.println("\nExplanation (optional, press Enter to skip):");
        String explanation = scanner.nextLine().trim();

        Question question = new Question(qText, options, correct, explanation);
        try {
            quizManager.addQuestion(quiz.getAccessCode(), question);
        } catch (InvalidAccessCodeException e) {
            ConsoleHelper.printError(e.getMessage());
            scanner.nextLine();
            return;
        }
        quiz.addQuestion(question);

        ConsoleHelper.printSuccess("Question added. Total: " + quiz.getQuestionCount() + " questions in ArrayList");
        scanner.nextLine();

        System.out.println("\n  [1] Add another question");
        System.out.println("  [2] Done — Activate Quiz");
        String next = InputValidator.readMenuInput(scanner, "\nEnter choice: ");
        if (next.equals("2")) {
            if (quiz.getQuestionCount() == 0) {
                ConsoleHelper.printError("Cannot activate quiz with zero questions!");
            } else {
                try {
                    quizManager.startSession(quiz.getAccessCode());
                    ConsoleHelper.printSuccess("Quiz activated! Code: " + quiz.getAccessCode());
                } catch (Exception e) {
                    ConsoleHelper.printError(e.getMessage());
                }
                scanner.nextLine();
            }
        }
        ConsoleHelper.clearScreen();
    }

    // ==================== STUDENT FLOW ====================

    private void studentFlow() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("STUDENT PANEL");

        String name = InputValidator.readString(scanner, "Enter your name: ");
        String code = InputValidator.readString(scanner, "Enter access code: ");

        Quiz quiz;
        try {
            quiz = quizManager.getQuizByCode(code);
        } catch (InvalidAccessCodeException e) {
            ConsoleHelper.printError(e.getMessage());
            scanner.nextLine();
            return;
        }

        QuizSession session = quizManager.getSession(code);
        if (session == null) {
            ConsoleHelper.printError("Quiz session not started yet. Ask admin to activate.");
            scanner.nextLine();
            return;
        }

        Student student = new Student(UUID.randomUUID().toString(), name);
        Attempt attempt = new Attempt(student, quiz);
        attempt.start();

        ConsoleHelper.printSuccess("Joined: " + quiz.getTitle());
        System.out.println("─".repeat(40));
        System.out.println("Questions: " + quiz.getQuestionCount() + "   |   Good luck!");
        System.out.println("─".repeat(40));
        scanner.nextLine();

        List<Question> questions = quiz.getQuestions();
        int qNum = 1;
        for (Question q : questions) {
            ConsoleHelper.clearScreen();
            displayQuestion(q, qNum, questions.size(), quiz.getTitle());
            int answer = InputValidator.readInt(scanner, 1, 4, "Your answer (1–4): ");
            attempt.recordAnswer(q, answer);

            ConsoleHelper.clearScreen();
            boolean correct = q.isCorrect(answer);
            if (correct) {
                ConsoleHelper.printSuccess("CORRECT!");
            } else {
                ConsoleHelper.printError("WRONG! Correct answer: [" + q.getCorrectOptionId() + "] " +
                        getOptionText(q, q.getCorrectOptionId()));
            }
            System.out.println("─".repeat(40));
            if (!q.getExplanation().isEmpty()) {
                System.out.println("Explanation:");
                System.out.println(q.getExplanation());
            }
            System.out.println("─".repeat(40));
            System.out.println("Press Enter for next question...");
            scanner.nextLine();
            qNum++;
        }

        attempt.finalise();
        quizManager.recordAttempt(code, attempt);

        ConsoleHelper.clearScreen();
        showScoreSummary(attempt);
        scanner.nextLine();
    }

    private void displayQuestion(Question q, int num, int total, String quizTitle) {
        ConsoleHelper.printHeader("Question " + num + " of " + total + "                    Quiz: " + quizTitle, 64);
        System.out.println();
        System.out.println(q.getQuestionText());
        System.out.println();
        for (Option opt : q.getOptions()) {
            System.out.println("  [" + opt.getOptionId() + "]  " + opt.getText());
        }
        System.out.println();
        System.out.println(ConsoleHelper.MAGENTA + "  [i] OOP Concept hint   Your answer (1–4): " + ConsoleHelper.RESET);
    }

    private String getOptionText(Question q, int optionId) {
        return q.getOptions().stream()
                .filter(o -> o.getOptionId() == optionId)
                .findFirst()
                .map(Option::getText)
                .orElse("");
    }

    private void showScoreSummary(Attempt attempt) {
        ConsoleHelper.printHeading("YOUR RESULTS — " + attempt.getStudent().getName());
        System.out.println();
        System.out.println("  Score       :  " + attempt.getScore() + " / " + attempt.getQuiz().getQuestionCount());
        System.out.println("  Time Taken  :  " + formatTime(attempt.getTimeTaken()));
        System.out.printf("  Percentage  :  %.1f%%%n",
                (attempt.getScore() * 100.0) / attempt.getQuiz().getQuestionCount());
        System.out.println();
        System.out.println("  Correct : " + attempt.getCorrectCount() +
                "    Wrong : " + attempt.getWrongCount() +
                "    Skipped : 0");
        System.out.println();
        ConsoleHelper.printFooter(64);

        System.out.println();
        System.out.println("  [1] View Leaderboard");
        System.out.println("  [2] Back to Main Menu");
        String choice = InputValidator.readMenuInput(scanner, "\nEnter choice: ");
        if (choice.equals("1")) {
            showLeaderboardFlow();
        }
    }

    // ==================== LEADERBOARD ====================

    private void showLeaderboardFlow() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("LEADERBOARD");

        // Collect attempts from all sessions
        List<Attempt> allAttempts = new ArrayList<>();
        for (QuizSession session : quizManager.getAllSessions()) {
            allAttempts.addAll(session.getAttempts());
        }

        if (allAttempts.isEmpty()) {
            ConsoleHelper.printWarning("No attempts yet. Be the first to take a quiz!");
            scanner.nextLine();
            return;
        }

        List<Attempt> ranked = scoreboard.rank(allAttempts);

        // Find max name length for formatting
        int maxNameLen = ranked.stream()
                .map(a -> a.getStudent().getName().length())
                .max(Integer::compareTo)
                .orElse(10);
        maxNameLen = Math.max(maxNameLen, 4);

        System.out.println();
        String header = String.format("  ╔═══╦%" + maxNameLen + "s╦═══════╦══════════════════════╣",
                "NAME");
        System.out.println(header);
        System.out.println("  ╠═══╬" + "═".repeat(maxNameLen + 2) + "╬═══════╬══════════════════════╣");
        System.out.printf("  ║ %-2s ║ %-" + maxNameLen + "s ║ %-5s ║ %-20s ║%n",
                "RANK", "NAME", "SCORE", "TIME");
        System.out.println("  ╠═══╬" + "═".repeat(maxNameLen + 2) + "╬═══════╬══════════════════════╣");

        for (int i = 0; i < ranked.size(); i++) {
            Attempt a = ranked.get(i);
            String rankSymbol = getRankSymbol(i + 1);
            String timeStr = formatTime(a.getTimeTaken());
            System.out.printf("  ║ %s ║ %-" + maxNameLen + "s ║ %d/%-3d ║ %-20s ║%n",
                    rankSymbol, a.getStudent().getName(), a.getScore(), a.getQuiz().getQuestionCount(), timeStr);
        }

        System.out.println("  ╚═══╩" + "═".repeat(maxNameLen + 2) + "╩═══════╩══════════════════════╝");
        System.out.println();
        System.out.println("  Tie-break rule: Equal score → faster time wins.");
        System.out.println();
        System.out.println("  [b] Back to Main Menu");
        scanner.nextLine();
        InputValidator.readMenuInput(scanner, "");
    }

    private String getRankSymbol(int rank) {
        return switch (rank) {
            case 1 -> "🥇 1";
            case 2 -> "🥈 2";
            case 3 -> "🥉 3";
            default -> String.format("  %2d", rank);
        };
    }

    private String formatTime(long millis) {
        long seconds = millis / 1000;
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long remainingSec = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSec);
    }

    // ==================== CONCEPT EXPLORER ====================

    private void conceptExplorer() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("OOP CONCEPT EXPLORER");
        System.out.println();
        System.out.println("  What would you like to learn?");
        System.out.println();
        System.out.println("  [1]  Abstraction       (User abstract class)");
        System.out.println("  [2]  Inheritance       (Admin & Student extend User)");
        System.out.println("  [3]  Encapsulation     (Question private fields)");
        System.out.println("  [4]  Polymorphism      (Comparator, method overriding)");
        System.out.println("  [5]  ArrayList         (Why dynamic array beats fixed array)");
        System.out.println("  [6]  Composition       (Quiz \"has-a\" ArrayList<Question>)");
        System.out.println("  [7]  Exception Handling (InvalidAccessCodeException)");
        System.out.println("  [8]  Enum Usage        (SessionStatus)");
        System.out.println("  [b]  Back");
        System.out.println();

        String choice = InputValidator.readMenuInput(scanner, "Enter choice: ");
        switch (choice) {
            case "1" -> showAbstraction();
            case "2" -> showInheritance();
            case "3" -> showEncapsulation();
            case "4" -> showPolymorphism();
            case "5" -> showArrayList();
            case "6" -> showComposition();
            case "7" -> showExceptionHandling();
            case "8" -> showEnumUsage();
            case "b" -> { return; }
            default -> ConsoleHelper.printError("Invalid choice.");
        }
        scanner.nextLine();
        conceptExplorer();
    }

    private void showAbstraction() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("ABSTRACTION");
        System.out.println();
        System.out.println("  What is Abstraction?");
        System.out.println();
        System.out.println("  Abstraction hides complex implementation details and");
        System.out.println("  exposes only essential features. In Java, we achieve this");
        System.out.println("  through abstract classes and interfaces.");
        System.out.println();
        System.out.println("  ───────────────── Example ─────────────────");
        System.out.println();
        System.out.println("  User.java is an abstract class. It defines common fields");
        System.out.println("  (id, name) and declares displayRoleInfo() as abstract —");
        System.out.println("  forcing every subclass to provide its own implementation.");
        System.out.println("  You cannot do 'new User()' — it is a contract only.");
        System.out.println();
        System.out.println("  Code location: com.eduquiz.model.User");
        System.out.println();
        System.out.println("  Press Enter to return...");
        scanner.nextLine();
    }

    private void showInheritance() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("INHERITANCE");
        System.out.println();
        System.out.println("  What is Inheritance?");
        System.out.println();
        System.out.println("  Inheritance allows a class to acquire fields and methods");
        System.out.println("  from a parent class. It creates an 'is-a' relationship.");
        System.out.println();
        System.out.println("  ───────────────── Example ─────────────────");
        System.out.println();
        System.out.println("  Admin.java and Student.java both extend User.java:");
        System.out.println("  'public class Admin extends User'");
        System.out.println();
        System.out.println("  They inherit getId(), getName(), setName() from User");
        System.out.println("  and override displayRoleInfo() with their own behavior.");
        System.out.println("  This avoids code duplication and establishes hierarchy.");
        System.out.println();
        System.out.println("  Press Enter to return...");
        scanner.nextLine();
    }

    private void showEncapsulation() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("ENCAPSULATION");
        System.out.println();
        System.out.println("  What is Encapsulation?");
        System.out.println();
        System.out.println("  Encapsulation bundles data and methods together while");
        System.out.println("  restricting direct access to fields. Fields are private,");
        System.out.println("  accessed only through public getters/setters.");
        System.out.println();
        System.out.println("  ───────────────── Example ─────────────────");
        System.out.println();
        System.out.println("  Question.java:");
        System.out.println("    private final String questionText;");
        System.out.println("    private final List<Option> options;");
        System.out.println("    private final int correctOptionId;");
        System.out.println();
        System.out.println("  These fields cannot be accessed directly from outside.");
        System.out.println("  Getters expose only what's needed. There are NO setters —");
        System.out.println("  once created, a Question is immutable. Thread-safe.");
        System.out.println();
        System.out.println("  Code location: com.eduquiz.model.Question");
        System.out.println();
        System.out.println("  Press Enter to return...");
        scanner.nextLine();
    }

    private void showPolymorphism() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("POLYMORPHISM");
        System.out.println();
        System.out.println("  What is Polymorphism?");
        System.out.println();
        System.out.println("  Polymorphism allows the same method call to behave");
        System.out.println("  differently based on the actual object type at runtime.");
        System.out.println();
        System.out.println("  ───────────────── Examples ─────────────────");
        System.out.println();
        System.out.println("  1. Admin and Student both override displayRoleInfo().");
        System.out.println("     Calling user.displayRoleInfo() executes the correct");
        System.out.println("     version based on actual object type.");
        System.out.println();
        System.out.println("  2. Scoreboard uses Comparator<Attempt> — a functional");
        System.out.println("     interface — to define sort order. We pass a method");
        System.out.println("     reference (Attempt::getScore) — polymorphism at work.");
        System.out.println();
        System.out.println("  Code locations: User subclasses, Scoreboard.java");
        System.out.println();
        System.out.println("  Press Enter to return...");
        scanner.nextLine();
    }

    private void showArrayList() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("ARRAYLIST VS ARRAY");
        System.out.println();
        System.out.println("  Why ArrayList over fixed array[]?");
        System.out.println();
        System.out.println("  Arrays have fixed size declared at creation. We don't know");
        System.out.println("  how many questions an Admin will add. ArrayList grows");
        System.out.println("  dynamically via add(). Internally, it manages a resizable");
        System.out.println("  backing array — automatic capacity management.");
        System.out.println();
        System.out.println("  ───────────────── Example ─────────────────");
        System.out.println();
        System.out.println("  In Quiz.java:");
        System.out.println("    private final ArrayList<Question> questions = new ArrayList<>();");
        System.out.println("    questions.add(q)  // no size limit");
        System.out.println();
        System.out.println("  O(1) amortized add, O(1) get by index. Perfect for our use.");
        System.out.println();
        System.out.println("  Code location: com.eduquiz.model.Quiz");
        System.out.println();
        System.out.println("  Press Enter to return...");
        scanner.nextLine();
    }

    private void showComposition() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("COMPOSITION");
        System.out.println();
        System.out.println("  What is Composition?");
        System.out.println();
        System.out.println("  Composition is a 'has-a' relationship where one object");
        System.out.println("  contains another as part of its state. Strong ownership.");
        System.out.println();
        System.out.println("  ───────────────── Example ─────────────────");
        System.out.println();
        System.out.println("  Quiz has-a ArrayList<Question>.");
        System.out.println("    Quiz 'owns' the questions — they don't exist without it.");
        System.out.println("  Attempt has-a Quiz and Student.");
        System.out.println("    Attempt cannot exist without both.");
        System.out.println();
        System.out.println("  This creates modular, testable components.");
        System.out.println();
        System.out.println("  Press Enter to return...");
        scanner.nextLine();
    }

    private void showExceptionHandling() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("EXCEPTION HANDLING");
        System.out.println();
        System.out.println("  How do we handle bad access codes?");
        System.out.println();
        System.out.println("  We use a custom checked exception:");
        System.out.println();
        System.out.println("  class InvalidAccessCodeException extends Exception");
        System.out.println();
        System.out.println("  QuizManager.getQuizByCode() throws this if the code isn't");
        System.out.println("  found in the HashMap. TerminalUI catches it and re-prompts.");
        System.out.println("  This separates error handling from business logic.");
        System.out.println();
        System.out.println("  Code location: com.eduquiz.exception.InvalidAccessCodeException");
        System.out.println();
        System.out.println("  Press Enter to return...");
        scanner.nextLine();
    }

    private void showEnumUsage() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeading("ENUM USAGE");
        System.out.println();
        System.out.println("  Why Enum for SessionStatus?");
        System.out.println();
        System.out.println("  Enums provide type-safe constants. You cannot create an");
        System.out.println("  invalid status. Better than using boolean flags or Strings.");
        System.out.println();
        System.out.println("  ───────────────── Example ─────────────────");
        System.out.println();
        System.out.println("  public enum SessionStatus { NOT_STARTED, ACTIVE, COMPLETED }");
        System.out.println();
        System.out.println("  QuizSession holds a SessionStatus field. The state machine");
        System.out.println("  is clear and enforced at compile time.");
        System.out.println();
        System.out.println("  Code location: com.eduquiz.model.SessionStatus");
        System.out.println();
        System.out.println("  Press Enter to return...");
        scanner.nextLine();
    }
}
