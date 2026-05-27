# Unit Testing Annotations Explained

When writing tests in Spring Boot using JUnit 5 and Mockito, we use several annotations to set up the environment. Here is a detailed breakdown of the annotations used in `OfficeServiceTest.java`.

---

## 1. `@ExtendWith(MockitoExtension.class)`
**Role:** The "Engine" or "Orchestrator".

*   **Why is it necessary?** JUnit 5 is a generic testing framework. It doesn't know anything about Mockito by default. This annotation tells JUnit: *"Please use the Mockito engine to process this class."*
*   **What happens if you remove it?** Your `@Mock` fields will be `null`, and your `@InjectMocks` will not receive any dependencies. It is the "switch" that turns on Mockito support.

---

## 2. `@DisplayName("Office Service Test")`
**Role:** The "Human Readable Label".

*   **Why is it necessary?** By default, IDEs and build reports show the class name (`OfficeServiceTest`). This annotation allows you to provide a more descriptive, professional name.
*   **What happens if you remove it?** The test still runs perfectly, but your test reports will just show the technical class name. It is purely for documentation and readability.

---

## 3. `@Mock`
**Role:** The "Fake Dependency".

*   **Why is it necessary?** In a **Unit Test**, you want to test ONLY the service logic. You don't want to connect to a real database. 
*   **What it does:** It creates a "hollow" or "fake" version of the `OfficeRepository`. It looks like the repository, but its methods do nothing unless you tell them what to do using `when(...)`.
*   **Example:** If you call `findAll()` on a `@Mock`, it returns an empty list or null by default, without ever touching the database.

---

## 4. `@InjectMocks`
**Role:** The "Automatic Assembly".

*   **Why is it necessary?** This identifies the actual class you are testing. Without it, the "fakes" (`@Mock`) are just floating around and aren't connected to your Service.
*   **The "Robot" Analogy:** 
    Imagine you are testing a **Robot** (`OfficeService`). The Robot cannot function without a **Battery** (`OfficeRepository`).
    1.  `@Mock` creates a "Fake Battery".
    2.  `@InjectMocks` tells Mockito: *"Take that Fake Battery and plug it into the Robot so I can test the Robot."*
*   **Manual vs. Automatic:**
    Without this annotation, you would have to write "boring" setup code manually:
    ```java
    // What @InjectMocks does for you behind the scenes:
    this.officeService = new OfficeService();
    this.officeService.setRepository(officeRepository); // Manual injection

    // The manual way (without @InjectMocks)
    @BeforeEach
    void setup() {
        this.officeService = new OfficeService();
        // You have to manually "inject" the mock into the service
        this.officeService.setRepository(officeRepository);
    }
    ```
*   **What it does:** It creates a real instance of your Service and automatically "injects" (plugs in) all the `@Mock` objects it finds in your test class.

---

## 5. `@Test`
**Role:** The "Entry Point".

*   **Why is it necessary?** Maven and your IDE search for this annotation to know which methods are actual test cases.
*   **What happens if you remove it?** The method will be treated as a regular helper method and will **never be executed** during the test phase. 

---

## Coordination Summary

1.  **`@ExtendWith`** starts the engine.
2.  **`@Mock`** creates the "dummy" tools (the Repository).
3.  **`@InjectMocks`** creates the "hero" (the Service) and gives it the dummy tools.
4.  **`@Test`** runs the specific scenario.
5.  **`@DisplayName`** makes the final report look pretty.
