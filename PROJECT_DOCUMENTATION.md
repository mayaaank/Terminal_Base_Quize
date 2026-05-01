# EduQuiz — Complete Project Documentation

## Overview

**EduQuiz** is an OOP-based Online Quiz Management System built as a Java console application. This project demonstrates core Object-Oriented Programming principles through a fully functional interactive quiz system with Admin and Student roles, leaderboards, and educational OOP concept explanations.

## Technology Stack

- **Language**: Java 17+ (LTS)
- **Build Tool**: Maven 3.9+
- **Testing**: JUnit 5
- **Architecture**: Pure Java (zero external dependencies beyond JUnit)

## Quick Start

```bash
# Compile the project
mvn compile

# Run the application
mvn exec:java

# Run all tests
mvn test
```

## Project Structure

```
src/main/java/com/eduquiz/
├── Main.java                          # Application entry point
├── model/                             # Domain models (Entities)
│   ├── User.java                     # Abstract base class
│   ├── Admin.java                    # Admin user (extends User)
│   ├── Student.java                  # Student user (extends User)
│   ├── Option.java                   # Question option (choice)
│   ├── Question.java                 # Question entity
│   ├── Quiz.java                     # Quiz container (has-a ArrayList<Question>)
│   ├── Attempt.java                  # Student attempt record
│   ├── QuizSession.java              # Active quiz session state
│   └── SessionStatus.java            # Enum for session states
├── service/                           # Business logic layer
│   ├── QuizService.java              # Service interface (abstraction)
│   ├── QuizManager.java              # Service implementation (HashMap storage)
│   └── Scoreboard.java               # Ranking service (Comparator polymorphism)
├── ui/
│   └── TerminalUI.java               # Terminal user interface (all I/O)
├── util/                              # Utility classes
│   ├── CodeGenerator.java            # Unique quiz code generation
│   ├── InputValidator.java           # Safe user input validation
│   └── ConsoleHelper.java            # Terminal formatting utilities
└── exception/
    └── InvalidAccessCodeException.java # Custom exception
```

## Layer Architecture

### 1. UI Layer (TerminalUI.java)
- **Purpose**: Handles ALL user interaction
- **Responsibilities**:
  - Display menus and prompts
  - Read user input
  - Format output with colors and ASCII art
  - Call service layer methods
- **Key Principle**: **NO business logic** — purely presentation

### 2. Service Layer (QuizManager.java, Scoreboard.java)
- **Purpose**: Implements business logic
- **Responsibilities**:
  - Quiz creation and management
  - Session lifecycle
  - Attempt recording
  - Leaderboard ranking
- **Key Principle**: **Separation of concerns** from UI

### 3. Model Layer (Model Classes)
- **Purpose**: Domain entities representing real-world concepts
- **Responsibilities**:
  - Store data
  - Enforce business rules (e.g., Question correctness)
  - Maintain object state
- **Key Principle**: **Encapsulation and immutability**

### 4. Utility Layer
- **Purpose**: Cross-cutting concerns
- **Responsibilities**:
  - Code generation
  - Input validation
  - Console formatting

## Core Features

### 1. Admin Mode: Create & Manage Quizzes
- Create a new quiz with title
- Add questions dynamically (4 options each, 1 correct)
- Generate unique access codes (format: QZ-X7R2)
- Activate quiz session for students
- See real-time question count in ArrayList

### 2. Student Mode: Take Quizzes
- Enter name and access code
- Join active quiz sessions
- Answer questions sequentially
- Get immediate feedback (correct/wrong)
- View explanations (if provided)
- See final score and time taken

### 3. Leaderboard: Ranked Results
- Score-based ranking (descending)
- Time-based tie-breaking (ascending)
- Top 3 with medals (🥇 🥈 🥉)
- Shows score as "X/Y" and formatted time

### 4. Concept Explorer: Learn OOP
- Interactive in-app tutorials
- 8 OOP concepts explained
- Links to actual code locations
- Accessible anytime via [i] key

## OOP Concepts Demonstrated

### 1. Abstraction
**What**: Hides implementation details, exposes essential features

**Where**:
- `User.java` — Abstract base class
- `QuizService.java` — Interface

**Example**:
```java
public abstract class User {
    private final String id;
    private final String name;
    
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Abstract method — forces subclasses to implement
    public abstract void displayRoleInfo();
    
    // Concrete methods with implementation
    public String getId() { return id; }
    public String getName() { return name; }
}
```

**Why**: Cannot `new User()` — it's a contract. Ensures every user type implements required behavior.

---

### 2. Inheritance
**What**: Child class acquires parent's fields and methods

**Where**:
- `Admin extends User`
- `Student extends User`

**Example**:
```java
public class Admin extends User {
    public Admin(String id, String name) {
        super(id, name);  // Call parent constructor
    }
    
    @Override
    public void displayRoleInfo() {
        System.out.println("Admin Role: Can create, add questions, activate quizzes");
    }
}
```

**Why**: Avoids duplicate code for `id`, `name`, `getId()`, `getName()`. Establishes hierarchy (Admin **is a** User, Student **is a** User).

---

### 3. Encapsulation
**What**: Bundles data + methods, restricts direct field access

**Where**:
- `Question.java` — Private fields, public getters
- `Quiz.java` — Private ArrayList, controlled access

**Example**:
```java
public class Question {
    // Private — cannot be accessed directly outside
    private final String questionText;
    private final List<Option> options;
    private final int correctOptionId;
    private final String explanation;
    
    // Constructor initializes everything
    public Question(String text, List<Option> opts, int correct, String exp) {
        this.questionText = text;
        this.options = new ArrayList<>(opts);
        this.correctOptionId = correct;
        this.explanation = exp;
    }
    
    // Public getters — controlled access
    public String getQuestionText() { return questionText; }
    public List<Option> getOptions() { return Collections.unmodifiableList(options); }
    public boolean isCorrect(int optionId) { return optionId == correctOptionId; }
    
    // NO setters — immutable after creation!
}
```

**Why**: Thread-safe, prevents invalid state, maintains invariants. Once created, a Question cannot be changed.

---

### 4. Polymorphism
**What**: Same interface, different implementations

**Where**:
- Method overriding (`displayRoleInfo()`)
- `Comparator<Attempt>` in Scoreboard

**Example 1 — Method Overriding**:
```java
User admin = new Admin("1", "Alice");
User student = new Student("2", "Bob");

// Same method call, different behavior
admin.displayRoleInfo();   // "Admin Role: Can create..."
student.displayRoleInfo(); // "Student Role: Can take quizzes"
```

**Example 2 — Functional Interface**:
```java
public List<Attempt> rank(List<Attempt> attempts) {
    return attempts.stream()
        .sorted(Comparator
            .comparing(Attempt::getScore).reversed()       // High score first
            .thenComparing(Attempt::getTimeTaken()))        // Fast time second
        .collect(Collectors.toList());
}
```

**Why**: One interface (`Comparator`) can compare anything. Method reference `Attempt::getScore` is polymorphic.

---

### 5. Composition
**What**: "Has-a" relationship (strong ownership)

**Where**:
- `Quiz has-a ArrayList<Question>`
- `Attempt has-a Quiz` and `has-a Student`

**Example**:
```java
public class Quiz {
    private final String title;
    private final String accessCode;
    // Quiz OWNS this list — questions don't exist without it
    private final ArrayList<Question> questions = new ArrayList<>();
    
    public void addQuestion(Question q) {
        questions.add(q);  // Dynamic growth
    }
}

public class Attempt {
    private final Student student;
    private final Quiz quiz;
    // Attempt cannot exist without both
}
```

**Why**: More flexible than inheritance. "Quiz is not a Question list" — Quiz **has** questions.

---

### 6. ArrayList vs Array
**What**: Dynamic array that grows automatically

**Where**:
- `Quiz.questions` — `ArrayList<Question>`

**Why ArrayList**:
```java
// Array — fixed size, must know count upfront
Question[] questions = new Question[10];  // What if admin adds 11?

// ArrayList — grows dynamically
ArrayList<Question> questions = new ArrayList<>();
questions.add(q1);  // No size limit
questions.add(q2);  // Amortized O(1)
```

**Benefits**:
- No size declaration needed
- Automatic capacity management
- O(1) get by index, O(1) amortized add
- Built-in methods (`size()`, `isEmpty()`, etc.)

**Internal**: When full, creates new larger array and copies elements.

---

### 7. HashMap Storage
**What**: Key-value store for fast lookups

**Where**:
- `QuizManager.quizzes` — `HashMap<String, Quiz>` (code → Quiz)
- `QuizManager.sessions` — `HashMap<String, QuizSession>` (code → Session)

**Why**:
```java
// O(1) lookup by access code
Quiz quiz = quizzes.get("QZ-K7R2");

// Compare to O(n) with ArrayList search
```

---

### 8. Exception Handling
**What**: Custom exception for invalid access codes

**Where**:
- `InvalidAccessCodeException.java`

**Example**:
```java
public class InvalidAccessCodeException extends Exception {
    public InvalidAccessCodeException(String code) {
        super("Invalid access code: " + code + ". Please check and try again.");
    }
}

// In QuizManager
public Quiz getQuizByCode(String code) throws InvalidAccessCodeException {
    Quiz quiz = quizzes.get(code);
    if (quiz == null) {
        throw new InvalidAccessCodeException(code);
    }
    return quiz;
}

// In TerminalUI — catch and handle gracefully
try {
    quiz = quizManager.getQuizByCode(code);
} catch (InvalidAccessCodeException e) {
    ConsoleHelper.printError(e.getMessage());
}
```

**Why**: Separates error handling from business logic. Caller decides how to handle.

---

### 9. Enum Usage
**What**: Type-safe constants

**Where**:
- `SessionStatus.java`

**Example**:
```java
public enum SessionStatus {
    NOT_STARTED,   // Quiz created, not yet active
    ACTIVE,        // Students can join and take quiz
    COMPLETED      // Quiz finished, no more attempts
}

// Usage in QuizSession
private SessionStatus status;

// Type-safe — compiler prevents invalid values
this.status = SessionStatus.ACTIVE;  // OK
this.status = "running";              // COMPILE ERROR
```

**Why**: Better than `boolean isActive, isCompleted` or `String status`. Prevents invalid states.

---

### 10. Interface (Abstraction)
**What**: Contract that classes can implement

**Where**:
- `QuizService.java`

**Example**:
```java
public interface QuizService {
    Quiz createQuiz(String title);
    void addQuestion(String code, Question q) throws InvalidAccessCodeException;
    Quiz getQuizByCode(String code) throws InvalidAccessCodeException;
    void startSession(String code);
    QuizSession getSession(String code);
    void recordAttempt(String code, Attempt attempt);
    Map<String, Quiz> getAllQuizzes();
    List<QuizSession> getAllSessions();
}

// QuizManager implements this contract
public class QuizManager implements QuizService {
    @Override
    public Quiz createQuiz(String title) {
        // Implementation here
    }
}
```

**Why**: Multiple implementations possible (e.g., `DatabaseQuizService`, `FileQuizService`). Dependency injection friendly.

---

## Detailed Flow

### Admin Flow
1. Select **[1] Host a New Quiz**
2. Enter quiz title
3. System creates quiz with unique code (e.g., `QZ-K7R2`)
4. Add questions:
   - Enter question text
   - Enter 4 options
   - Specify correct answer (1-4)
   - Optional explanation
5. View real-time count: "X questions in ArrayList"
6. **[2] Activate & Finish** — Opens session for students
7. Share code with students

### Student Flow
1. Select **[2] Join an Active Quiz**
2. Enter name
3. Enter access code (e.g., `QZ-K7R2`)
4. System validates code and session status
5. Questions displayed one by one:
   - Read question
   - Select answer (1-4)
   - Immediate feedback (correct/wrong)
   - Explanation shown
6. After last question:
   - Score summary (X/Y, percentage, time)
   - Option to view leaderboard

### Leaderboard Flow
1. Select **[3] View Leaderboard**
2. System collects all attempts from all sessions
3. Sorts by:
   - Score (descending)
   - Time (ascending) — tie-breaker
4. Displays formatted table with ranks
5. Top 3: 🥇 🥈 🥉

### Concept Explorer
Accessible via **[4]** or **[i]** key anytime:
- Learn about 8 OOP concepts
- Interactive explanations
- Real code examples
- Links to actual classes

## Key Design Decisions

### 1. Why ArrayList Over LinkedList?
- Frequent random access (`get(i)`) for questions
- O(1) get by index vs O(n) for LinkedList
- Better cache locality
- Add only at end (amortized O(1))

### 2. Why HashMap Over TreeMap?
- O(1) lookup by access code
- No need for sorted keys
- Simpler implementation

### 3. Why Immutable Question?
- Thread-safe (can share across sessions)
- Prevents accidental modification
- Once created, question text shouldn't change

### 4. Why Custom Exception?
- Type-safe error handling
- Caller can catch specific exception
- Can add custom fields/methods later

### 5. Why Interface for Service?
- Abstraction layer
- Easy to swap implementations
- Testable with mocks
- Follows dependency inversion

## Testing Strategy

### Test Coverage
```
src/test/java/com/eduquiz/
├── model/
│   ├── QuestionTest.java    — isCorrect(), immutability
│   ├── QuizTest.java        — addQuestion(), getQuestionCount()
│   ├── AttemptTest.java     — recordAnswer(), scoring
│   └── ...
├── service/
│   ├── QuizManagerTest.java — create, add, start, record
│   └── ScoreboardTest.java  — rank() ordering
└── util/
    └── CodeGeneratorTest.java — format, uniqueness
```

### Run Tests
```bash
mvn test
```

### Test Reports
```
target/surefire-reports/
├── TEST-*.xml  # JUnit XML reports
└── TEST-*.txt  # Human-readable results
```

## Code Generation Algorithm

**Format**: `QZ-XX1X` or `QZ-X1XX` (2 letters, 2 digits, mixed)

**Algorithm**:
1. Random uppercase letters (A-Z)
2. Random digits (0-9)
3. Pattern: Letter-Letter-Char-Char or variations
4. Check uniqueness in HashMap
5. Regenerate if collision

**Why Not UUID**?
- Shorter, user-friendly
- Easy to read/communicate
- Less typing for students

## Input Validation

All user input validated:
- **Menu choices**: Must match pattern
- **Numbers**: Range checking (1-4, etc.)
- **Strings**: Non-empty, trimmed
- **Access codes**: Existence check in HashMap

## Terminal Formatting

**ANSI Colors**:
```java
public class ConsoleHelper {
    public static final String RESET = "\u001B[0m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String BOLD = "\u001B[1m";
}
```

**Screen Management**:
- Clear screen before each section
- Header/footer separators
- Box drawing characters for tables
- Consistent spacing

## Session Management

### QuizSession States
```
NOT_STARTED → ACTIVE → COMPLETED
```

### Lifecycle
1. Quiz created → `NOT_STARTED`
2. Admin activates → `ACTIVE`
3. Students join and take quiz
4. All attempts recorded
5. Session remains `ACTIVE` for viewing

### Attempt Recording
- Timestamp on `start()`
- Timestamp on `finalise()`
- `timeTaken = end - start`
- Score calculated from correct answers

## Common Issues & Solutions

### Issue: "Cannot activate quiz with zero questions"
**Solution**: Add at least one question before activating

### Issue: "Invalid access code"
**Solution**: Check code exactly (case-sensitive), ensure quiz is activated

### Issue: Leaderboard empty
**Solution**: Students must complete at least one quiz attempt

### Issue: Scanner skipping input
**Solution**: Use `scanner.nextLine()` appropriately after `nextInt()`

## Extension Ideas

### For Future Enhancement
1. **Persistence**: Save to file/database
2. **Timer**: Countdown per question
3. **Categories**: Multiple quiz topics
4. **Multiplayer**: Concurrent sessions
5. **GUI**: Swing/JavaFX interface
6. **Export**: Results to CSV/PDF
7. **Question Bank**: Reuse questions across quizzes
8. **Analytics**: Performance charts

## Best Practices Demonstrated

1. **DRY (Don't Repeat Yourself)**: Inheritance eliminates duplication
2. **SOLID Principles**: Single responsibility, dependency inversion
3. **Immutable Objects**: Thread-safe Questions
4. **Fail-Fast**: Early validation in constructors
5. **Defensive Copies**: `new ArrayList<>(opts)` prevents external modification
6. **Unmodifiable Views**: `Collections.unmodifiableList()`
7. **Separation of Concerns**: UI vs Service vs Model
8. **Exception Handling**: Custom exceptions, graceful recovery
9. **Type Safety**: Enums instead of strings
10. **Documentation**: Clear comments, JavaDoc ready

## Performance Characteristics

| Operation | Complexity | Notes |
|-----------|-----------|-------|
| Add question | O(1) amortized | ArrayList add() |
| Get question | O(1) | ArrayList get() |
| Lookup quiz | O(1) | HashMap get() |
| Rank attempts | O(n log n) | Stream sort |
| Validate code | O(1) | HashMap containsKey() |

## Memory Usage

- All data in-memory (no persistence)
- Lightweight objects
- Suitable for hundreds of questions/students
- GC friendly (no memory leaks)

## Thread Safety

- **Immutable objects**: Question, Option, Attempt (after finalise)
- **Not thread-safe**: QuizManager (single-threaded console app)
- **Safe for**: Multiple Student objects reading same Quiz

## Academic Use

This project demonstrates:
- **Core Java**: Classes, interfaces, inheritance, polymorphism
- **OOP Principles**: All 4 pillars with practical examples
- **Collections**: ArrayList, HashMap, List
- **Exception Handling**: Custom exceptions
- **Design Patterns**: Factory (CodeGenerator), Strategy (Comparator), MVC pattern
- **Testing**: JUnit 5 test cases
- **Build Tools**: Maven dependency management

## Resources

- **README.md**: Quick start guide
- **PROJECT_DOCUMENTATION.md**: This file — comprehensive guide
- **Inline Comments**: Code-level documentation
- **Concept Explorer**: Interactive learning in-app

## Conclusion

EduQuiz is a complete, working quiz management system that serves as:
1. **Educational tool**: For students to learn OOP concepts
2. **Practical application**: Real quiz-taking platform
3. **Demonstration project**: Shows proper Java architecture
4. **Learning resource**: Well-documented, clean code

All OOP principles are applied intentionally with clear examples in the codebase, making this an excellent reference project for understanding object-oriented design in Java.

---

**Author**: Academic Mini Project  
**Version**: 1.0  
**Last Updated**: 2026-05-02