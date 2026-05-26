# Maven CLI Guide (Manual Sync)

In IntelliJ IDEA, the "Sync" button automates Maven tasks. If you are working from the terminal or a CI environment, use these commands to manage your dependencies manually.

## 1. The Standard "Sync"
This is the most common command to ensure your project is compiled and dependencies are ready.
```bash
./mvnw install -DskipTests
```
- **Goal:** Compiles code, downloads jars, and installs the project to your local repository.
- **`-DskipTests`:** Skips running tests to make the process much faster.

## 2. Dependency Resolution (Download Only)
If you only want to download new dependencies without building the project:
```bash
./mvnw dependency:resolve
```

## 3. Force Update (The "Rescue" Command)
Use this if Maven seems to be using an old version of a dependency or if a download was corrupted.
```bash
./mvnw clean install -U -DskipTests
```
- **`-U` (Update):** Forces Maven to check remote repositories for updated releases and snapshots.
- **`clean`:** Deletes the `target/` folder to ensure a fresh build.

## 4. Cleaning the Project
If you want to remove all compiled files and start from scratch:
```bash
./mvnw clean
```

## 5. Running Tests
To run your full test suite:
```bash
./mvnw test
```

---

### Important: Why use `./mvnw` instead of `mvn`?
The `mvnw` file in your root directory is the **Maven Wrapper**.
1. **Consistency:** It ensures everyone on the project uses the same Maven version.
2. **Zero Setup:** You don't need to install Maven on your system; the wrapper downloads it automatically the first time you run it.
3. **Best Practice:** Always prefer `./mvnw` for project-specific tasks.
