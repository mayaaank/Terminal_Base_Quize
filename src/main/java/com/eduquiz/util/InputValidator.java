package com.eduquiz.util;

import java.util.Scanner;

/**
 * Utility for safe input reading with validation and re-prompting.
 * Centralizes all Scanner interactions — TerminalUI owns the Scanner instance.
 */
public class InputValidator {
    private InputValidator() {
        // prevent instantiation
    }

    /**
     * Reads an integer within min-max inclusive.
     *
     * @param scanner Scanner instance
     * @param min minimum acceptable value
     * @param max maximum acceptable value
     * @param prompt message to display
     * @return validated integer
     */
    public static int readInt(Scanner scanner, int min, int max, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Reads a non-empty string.
     *
     * @param scanner Scanner instance
     * @param prompt message to display
     * @return trimmed non-empty string
     */
    public static String readString(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Reads a menu choice from user.
     *
     * @param scanner Scanner instance
     * @param prompt message to display
     * @return user input string
     */
    public static String readMenuInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
