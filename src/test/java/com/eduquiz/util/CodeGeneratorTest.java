package com.eduquiz.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CodeGenerator.
 */
class CodeGeneratorTest {

    @Test
    void testGenerate_formatIsQZXXXX() {
        Set<String> codes = new HashSet<>();
        String code = CodeGenerator.generate(codes);

        assertTrue(code.startsWith("QZ-"));
        assertEquals(7, code.length());  // QZ-XXXX = 7 chars
        String suffix = code.substring(3);
        assertTrue(suffix.matches("[A-Z0-9]{4}"));
    }

    @Test
    void testGenerate_avoidsCollisions() {
        Set<String> existing = new HashSet<>();
        existing.add("QZ-AAAA");
        existing.add("QZ-BBBB");

        // generate 100 codes, none should collide
        for (int i = 0; i < 100; i++) {
            String code = CodeGenerator.generate(existing);
            assertFalse(existing.contains(code));
            existing.add(code);  // add to set to prevent duplicates in subsequent iterations
        }
    }

    @Test
    void testGenerate_producesUniqueCodes() {
        Set<String> codes = new HashSet<>();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            codes.add(CodeGenerator.generate(codes));
        }
        assertEquals(count, codes.size());
    }
}
