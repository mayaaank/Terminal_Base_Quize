package com.eduquiz.util;

/**
 * Helper for terminal output formatting.
 * Colors, boxes, headers, dividers — all UI decoration.
 */
public class ConsoleHelper {
    // ANSI Color codes
    public static final String RESET = "\033[0m";
    public static final String BOLD = "\033[1m";
    public static final String DIM = "\033[2m";
    public static final String CYAN = "\033[0;36m";
    public static final String YELLOW = "\033[0;33m";
    public static final String GREEN = "\033[0;32m";
    public static final String RED = "\033[0;31m";
    public static final String BLUE = "\033[0;34m";
    public static final String MAGENTA = "\033[0;35m";
    public static final String WHITE = "\033[0;37m";

    private ConsoleHelper() {
        // prevent instantiation
    }

    /**
     * Clears the terminal screen.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prints a horizontal separator line.
     *
     * @param length line length
     */
    public static void printSeparator(int length) {
        System.out.println(DIM + "  " + "-".repeat(length) + RESET);
    }

    /**
     * Prints a boxed header.
     *
     * @param title header text
     * @param width total width of box (including borders)
     */
    public static void printHeader(String title, int width) {
        String line = "═".repeat(width - 4);
        System.out.println(CYAN + BOLD);
        System.out.println("  ╔════" + line + "════╗");
        System.out.println("  ║  " + center(title, width - 8) + "  ║");
        System.out.println("  ╠════" + line + "════╣" + RESET);
    }

    /**
     * Prints a box footer.
     *
     * @param width total width
     */
    public static void printFooter(int width) {
        String line = "═".repeat(width - 4);
        System.out.println(CYAN + BOLD + "  ╚════" + line + "════╝" + RESET);
    }

    /**
     * Centers text within given width.
     *
     * @param text input text
     * @param width target width
     * @return centered text
     */
    public static String center(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int pad = (width - text.length()) / 2;
        return " ".repeat(pad) + text + " ".repeat(width - text.length() - pad);
    }

    /**
     * Prints success message with checkmark.
     *
     * @param message the message
     */
    public static void printSuccess(String message) {
        System.out.println("  " + GREEN + "[✔]" + RESET + " " + message);
    }

    /**
     * Prints error message with cross.
     *
     * @param message the message
     */
    public static void printError(String message) {
        System.out.println("  " + RED + "[✘]" + RESET + " " + message);
    }

    /**
     * Prints a warning message.
     *
     * @param message the message
     */
    public static void printWarning(String message) {
        System.out.println("  " + YELLOW + "[!]" + RESET + " " + message);
    }

    /**
     * Prints info/magenta bracket.
     *
     * @param message the message
     */
    public static void printInfo(String message) {
        System.out.println("  " + MAGENTA + "[i]" + RESET + " " + message);
    }

    /**
     * Prints a bullet with green diamond.
     *
     * @param message the message
     */
    public static void printBullet(String message) {
        System.out.println("    " + GREEN + "◆" + RESET + " " + message);
    }

    /**
     * Prints a main heading with cyan equals signs.
     *
     * @param message the heading
     */
    public static void printHeading(String message) {
        System.out.println();
        System.out.println(CYAN + BOLD + "══════════════════════════════════════════════════════════" + RESET);
        System.out.println(CYAN + BOLD + "  " + message + RESET);
        System.out.println(CYAN + BOLD + "══════════════════════════════════════════════════════════" + RESET);
    }

    /**
     * Prints a subheading in yellow.
     *
     * @param message the subheading
     */
    public static void printSubheading(String message) {
        System.out.println();
        System.out.println(YELLOW + BOLD + "  ▸ " + message + RESET);
    }
}
