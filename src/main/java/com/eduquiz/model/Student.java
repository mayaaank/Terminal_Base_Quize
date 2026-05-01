package com.eduquiz.model;

/**
 * Student user extending User.
 * Inheritance: inherits id, name, getId(), setName() from User.
 * Polymorphism: overrides displayRoleInfo() with student-specific behavior.
 */
public class Student extends User {
    /**
     * Constructs a Student with given ID and name.
     */
    public Student(String id, String name) {
        super(id, name);
    }

    @Override
    public void displayRoleInfo() {
        System.out.println("Role: Student — can join quizzes and attempt them.");
    }
}
