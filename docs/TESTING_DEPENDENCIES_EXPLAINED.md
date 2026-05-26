# Testing Dependencies Explained

In the Java/Spring Boot ecosystem, testing libraries are modular. This guide explains the differences between common dependencies and how Spring Boot simplifies them.

## 1. Where is the "Test Starter"?
In your `pom.xml`, the dependency is located at lines **61-65**:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

**What it does:** This is a "Meta-Dependency". It doesn't contain code itself, but it instructs Maven to download everything you need for testing (JUnit, Mockito, AssertJ, etc.) in one go.

---

## 2. JUnit modularity

| Dependency | Purpose | Advantage | Disadvantage |
| :--- | :--- | :--- | :--- |
| **`junit-jupiter-api`** | Only the annotations (`@Test`) and Assertions. | Extremely lightweight. | Cannot actually *run* the tests by itself. |
| **`junit-jupiter`** | **Aggregator**: Includes API + Engine + Params. | One-stop shop. Guaranteed to work in IDEs and CLI. | Slightly more dependencies downloaded. |

---

## 3. Mockito modularity

| Dependency | Purpose | Advantage | Disadvantage |
| :--- | :--- | :--- | :--- |
| **`mockito-core`** | The base engine for creating mocks. | Can be used in any project (even outside tests). | Requires manual setup (boilerplate code). |
| **`mockito-junit-jupiter`** | The bridge for JUnit 5. | Integrates with JUnit lifecycle. Enables `@ExtendWith`. | Specific to JUnit 5. |

---

## 4. Using Mockito Extensions

### WITHOUT `@ExtendWith` (The "Manual" Way)
You have to manually open and close the "mocks session". If you forget, your mocks will be `null`.

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    private AutoCloseable closeable;

    @BeforeEach
    void init() {
        // You MUST call this manually
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSomething() {
        // ... test logic
    }
}
```

### WITH `@ExtendWith` (The "Clean" Way)
The extension handles the lifecycle for you. No `BeforeEach` is needed to initialize mocks.

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // The "Magic" happens here
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Test
    void testSomething() {
        // 'repository' is already initialized and ready!
    }
}
```

---

## Summary
In a Spring Boot project, **you don't need to add any of these manually**. The `spring-boot-starter-test` already provides the "Clean Way" (`junit-jupiter` + `mockito-junit-jupiter`) out of the box.
