package com.eduquiz.model;

/**
 * Admin user extending User.
 * Inheritance: inherits id, name, getId(), setName() from User.
 * Polymorphism: overrides displayRoleInfo() with admin-specific behavior.
 */
public class Admin extends User {
    /**
     * Constructs an Admin with given ID and name.
     */
    public Admin(String id, String name) {
        super(id, name);
    }

    @Override
    public void displayRoleInfo() {
        System.out.println("Role: Administrator — can create and manage quizzes.");
    }
}
