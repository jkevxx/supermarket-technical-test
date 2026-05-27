# JaCoCo Test Coverage Guide

This guide explains what JaCoCo is, why it is configured as a plugin, and how to use it to see your test coverage.

## 1. What is JaCoCo?
**JaCoCo** stands for **Ja**va **Co**de **Co**verage. It is a tool that analyzes your compiled code while your tests are running to see which lines of code were executed and which were skipped.

## 2. Why is it a "Plugin" and not a "Dependency"?

| Type | Analogy | Purpose |
| :--- | :--- | :--- |
| **Dependency** | **Ingredients:** A library you "import" into your code to use its functions (e.g., Spring, Mockito). | Code your app needs to **RUN**. |
| **Plugin** | **Tools:** A program that runs *on* your project during the build process (e.g., JaCoCo, Compiler). | Tasks your app needs to **BUILD** or **ANALYZE**. |

JaCoCo is a plugin because it doesn't provide functions for your application to call. Instead, it "watches" your application from the outside during the `test` phase.

## 3. How it is configured in `pom.xml`
The plugin has two main "executions":
1. **`prepare-agent`**: This runs before the tests start. It sets up a "spy" (the agent) that will follow the execution.
2. **`report`**: This runs after the tests finish. It takes the data collected by the spy and creates a readable HTML report.

## 4. How to generate the report
To see your coverage, you must run the tests using Maven:

```bash
./mvnw test
```

## 5. Where to find the report
Once the command finishes, Maven creates a new folder in your project called `target`.
The report is located at:
`target/site/jacoco/index.html`

**To view it:**
1. Open your file explorer.
2. Navigate to `target/site/jacoco/`.
3. Right-click `index.html` and select **"Open with Browser"**.

## 6. How to read the report
- **Green:** Line was fully covered by tests.
- **Yellow:** Line has a branch (like an `if` statement) where only one side was tested.
- **Red:** Line was never executed during the tests.
