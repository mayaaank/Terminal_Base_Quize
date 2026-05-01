package com.eduquiz.model;

/**
 * Abstract base class representing a system user.
 * Demonstrates Abstraction: common state and behavior for all user types.
 * Demonstrates Inheritance: Admin and Student extend this class.
 */
public abstract class User {
    private final String id;
    private String name;

    /**
     * Constructs a User with unique ID and name.
     *
     * @param id unique identifier
     * @param name display name
     * @throws IllegalArgumentException if id or name is null/empty
     */
    public User(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        this.id = id.trim();
        this.name = name.trim();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        this.name = name.trim();
    }

    /**
     * Abstract method that each user type must implement.
     * Demonstrates Polymorphism: different behavior per subclass.
     */
    public abstract void displayRoleInfo();
}
