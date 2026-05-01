package com.eduquiz;

import com.eduquiz.ui.TerminalUI;

/**
 * EduQuiz — OOP-Based Online Quiz Management System
 * Entry point: wires together all layers and launches the TUI.
 *
 * Build: mvn compile exec:java
 * Test:  mvn test
 */
public class Main {
    public static void main(String[] args) {
        TerminalUI ui = new TerminalUI();
        ui.start();
    }
}
