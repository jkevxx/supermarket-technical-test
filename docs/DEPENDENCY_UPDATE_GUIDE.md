# Manual Dependency Update Guide

This guide outlines the steps to manually identify and update vulnerable dependencies in this Spring Boot project.

## Why updating the "Parent" fixes everything
If you are coming from an **NPM/Node.js** background, Maven's dependency management (especially with Spring Boot) works differently:

### 1. The Bill of Materials (BOM) Concept
In NPM, you typically manage versions for every package individually in `package.json`. In Spring Boot, the `spring-boot-starter-parent` acts as a **Bill of Materials (BOM)**. 
- It contains a massive list of hundreds of libraries (Hibernate, Jackson, Tomcat, Spring Data, etc.) that have been tested to work perfectly together.
- When you update the Parent version, you are updating the "Source of Truth" for all these versions simultaneously.

### 2. Version Inheritance
Notice that in our `pom.xml`, the dependency for JPA doesn't have a `<version>` tag:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <!-- No version here! -->
</dependency>
```
Maven looks at the Parent, finds the version of `spring-boot-starter-data-jpa` that corresponds to Spring Boot `3.5.14`, and uses that. This prevents "Dependency Hell" where different libraries might require conflicting versions of the same transitive dependency.

### 3. Maven vs. NPM Comparison
| Feature | NPM / Node.js | Maven / Spring Boot |
| :--- | :--- | :--- |
| **Version Control** | Individual versions in `package.json`. | Centralized versions via `<parent>` or BOM. |
| **Conflicts** | Can have multiple versions of the same lib in `node_modules`. | Only **one** version of a library can exist in the classpath. |
| **Transitive Libs** | Managed by the package itself. | Coordinated by the Spring Boot team for compatibility. |

---

## Step 1: Identify the Vulnerability
Vulnerabilities are often found in transitive dependencies (libraries pulled in by "starters" like `spring-boot-starter-data-jpa`).
- **Tooling:** Use `mvn dependency:tree` to see the full hierarchy.
- **Scanning:** Use tools like `mvn dependency-check:check` (OWASP) or GitHub's Dependabot alerts.

## Step 2: Research Fixed Versions
Once a vulnerability (e.g., CVE-2026-40973) is identified:
1. Visit the [Spring Quick Start](https://spring.io/projects/spring-boot#learn) or [Maven Central](https://mvnrepository.com/).
2. Look for the latest patch version in your current major/minor line (e.g., if on 3.5.9, look for 3.5.x).
3. Verify the release notes for security patches.

## Step 3: Update the `pom.xml`
In most Spring Boot projects, the version is managed by the `spring-boot-starter-parent`.

1. Open `pom.xml`.
2. Locate the `<parent>` block:
   ```xml
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.5.14</version> <!-- Update this version -->
       <relativePath/>
   </parent>
   ```
3. Change the `<version>` to the desired secure version.

## Step 4: Verify the Update
After modifying the `pom.xml`, ensure the application still builds and functions correctly.

1. **Clear local cache (optional but recommended):**
   ```bash
   ./mvnw clean
   ```
2. **Run Tests:**
   ```bash
   ./mvnw test
   ```
3. **Check Effective Version:**
   Verify that the underlying dependencies (like Hibernate) have actually updated:
   ```bash
   ./mvnw dependency:list | grep hibernate
   ```

## Step 5: Final Security Check
Run your security scanner again to ensure the vulnerability is no longer reported.
