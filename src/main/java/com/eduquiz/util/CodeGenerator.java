package com.eduquiz.util;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

/**
 * Generates unique quiz access codes with collision detection.
 * Format: QZ-XXXX where X is alphanumeric (excluding confusing chars).
 */
public class CodeGenerator {
    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private CodeGenerator() {
        // prevent instantiation
    }

    /**
     * Generates a unique access code not present in existingCodes.
     *
     * @param existingCodes set of codes already in use
     * @return new unique code
     */
    public static String generate(Set<String> existingCodes) {
        String code;
        int attempts = 0;
        do {
            code = "QZ-" + randomChars(4);
            attempts++;
            if (attempts > 1000) {
                throw new IllegalStateException("Too many code generation attempts — check collision handling");
            }
        } while (existingCodes.contains(code));
        return code;
    }

    /**
     * Generates random string of given length from CHARS.
     *
     * @param len length of random string
     * @return random alphanumeric string
     */
    private static String randomChars(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
