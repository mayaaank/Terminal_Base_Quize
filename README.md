# EduQuiz — OOP-Based Online Quiz Management System

A Java console application demonstrating core OOP principles (Encapsulation, Inheritance, Polymorphism, Abstraction) through an interactive quiz system.

## Features

- **Admin Mode**: Create quizzes, add questions dynamically, generate unique access codes
- **Student Mode**: Join quizzes by code, answer questions, see immediate feedback
- **Leaderboard**: Ranked results by score (descending) and time (ascending)
- **Concept Explorer**: Interactive OOP learning module explaining design choices
- **ArrayList-Based**: Dynamic question storage with no fixed size limit

## Requirements

- Java 17+ (LTS)
- Maven 3.9+

## Build & Run

```bash
# Compile
mvn compile

# Run the application
mvn exec:java

# Run tests
mvn test
```

## Project Structure

```
src/main/java/com/eduquiz/
├── Main.java                          # Entry point
├── model/                             # OOP model classes
│   ├── User.java                     # Abstract base class
│   ├── Admin.java, Student.java      # Inheritance
│   ├── Option.java, Question.java    # Encapsulation
│   ├── Quiz.java                     # Composition + ArrayList
│   ├── Attempt.java                  # Session data
│   ├── QuizSession.java              # Lifecycle management
│   └── SessionStatus.java            # Enum state machine
├── service/                           # Business logic
│   ├── QuizService.java              # Interface (Abstraction)
│   ├── QuizManager.java              # HashMap-based implementation
│   └── Scoreboard.java               # Comparator-based ranking (Polymorphism)
├── ui/
│   └── TerminalUI.java               # All console I/O
├── util/
│   ├── CodeGenerator.java            # Unique code generation
│   ├── InputValidator.java           # Safe input handling
│   └── ConsoleHelper.java            # Terminal formatting
└── exception/
    └── InvalidAccessCodeException.java
```

## OOP Concepts Demonstrated

| Concept      | Where it appears                          | Why it matters |
|--------------|-------------------------------------------|----------------|
| Abstraction  | `User` abstract class, `QuizService` interface | Hides complexity, enforces contracts |
| Inheritance  | `Admin` and `Student` extend `User`      | Reuse + extensibility |
| Encapsulation| Private fields with getters in `Question`, `Quiz` | Controlled access, immutability |
| Polymorphism | `displayRoleInfo()` override, `Comparator` | Single interface, multiple behaviors |

## Demo Flow

1. **Admin** creates quiz → note access code (e.g. `QZ-K7R2`)
2. **Admin** adds 4+ questions; see `ArrayList` grow live
3. **Student 1** joins with code, completes quiz
4. **Student 2** joins, completes quiz
5. **Leaderboard** shows sorted results (score ↓, time ↑)
6. **Concept Explorer** ([i] key) explains OOP pillars inside the app

## Sample Questions (built during demo)

```
Q: What does OOP stand for?
   [1] Object Oriented Programming  ← correct
   [2] Open Object Protocol
   [3] Ordered Output Process
   [4] Operating Output Plugin

Explanation: OOP stands for Object Oriented Programming...
```

## Testing

Unit tests cover core domain logic using JUnit 5:
- `Question.isCorrect()`
- `Quiz.addQuestion()` / `getQuestionCount()`
- `Attempt.recordAnswer()` and scoring
- `Scoreboard.rank()` ordering
- `CodeGenerator` format and collision avoidance

Run: `mvn test`

## Notes

- No persistence — all data lives in memory for the session
- Single-threaded, sequential student flow
- Designed for 80-column terminals; ANSI colors for clarity
- Zero external libraries beyond JUnit (pure Java)

## License

Academic project — free to use, modify, and present.
