# Teckro Tech Test — Craigslist Housing Search

Browser automation tests for the [Madrid Craigslist Housing](https://madrid.craigslist.org/) page, verifying sort functionality.

Built with **Java 25**, **Playwright**, **JUnit 5**, **Allure**, and **Claude Code**.

---

## Prerequisites

| Tool | Minimum version | Check |
|------|----------------|-------|
| Java | 21, 25, or 26 | `java -version` |
| Maven | 3.6 | `mvn -version` |

Both must be available on your `PATH`.

---

## Installing Java (Windows)

### 1. Download the JDK

1. Go to https://www.oracle.com/java/technologies/downloads/ and click the **Windows** tab
2. Choose a JDK version — the available options are **Java 21**, **Java 25**, or **Java 26** — and download either:
   - **`.exe`** — recommended, double-click to run the installer
   - **`.msi`** — Windows Installer package, also runs a guided install
   - **`.zip`** — manual option, requires you to extract and configure `JAVA_HOME` yourself (see step 2 below)
3. Run the installer and follow the prompts. The JRE is included with the JDK install.

### 2. Set JAVA_HOME and update PATH

1. Open **Start** → search **Environment Variables** → click **Edit the system environment variables**
2. Click **Environment Variables...**
3. Under **System variables**, click **New** and add:
   - Variable name: `JAVA_HOME`
   - Variable value: path to your JDK install, e.g. `C:\Program Files\Java\jdk-21` (adjust to match your installed version)
4. Under **System variables**, find `Path`, click **Edit**, then **New**, and add: `%JAVA_HOME%\bin`
5. Click OK to save, then open a new terminal and verify:

```bash
java -version
```

### 3. Install the Java extension in VS Code

1. Open VS Code
2. Go to **Extensions** (`Ctrl+Shift+X`)
3. Search for **Extension Pack for Java** (published by Microsoft) and click **Install**
4. VS Code will prompt you to configure a JDK — point it to your `JAVA_HOME` path, or let it detect it automatically

---

## Installing Maven (Windows)

### 1. Download Maven

1. Go to https://maven.apache.org/download.cgi and download the **Binary zip archive** (`apache-maven-x.x.x-bin.zip`)
2. Extract it to a permanent location, e.g. `C:\Program Files\Apache\maven`

### 2. Set MAVEN_HOME and update PATH

1. Open **Start** → search **Environment Variables** → click **Edit the system environment variables**
2. Click **Environment Variables...**
3. Under **System variables**, click **New** and add:
   - Variable name: `MAVEN_HOME`
   - Variable value: `C:\Program Files\Apache\maven` (or wherever you extracted it)
4. Under **System variables**, find `Path`, click **Edit**, then **New**, and add: `%MAVEN_HOME%\bin`
5. Click OK to save, then open a new terminal and verify:

```bash
mvn -version
```

### 3. Install the Maven extension in VS Code

1. Open VS Code
2. Go to **Extensions** (`Ctrl+Shift+X`)
3. Search for **Maven for Java** (published by Microsoft) and click **Install**
4. The Maven extension will use the `mvn` command from your `PATH` automatically

---

## Setup

### 1. Install dependencies

```bash
mvn install -DskipTests
```

Downloads Playwright, JUnit, and Allure from Maven Central into your local `.m2` cache.

### 2. Install Playwright browser binaries

This is a one-time step. Playwright bundles its own browsers separately from Maven:

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chromium"
```

To install all browsers (Chromium, Firefox, WebKit):

```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

---

## Running tests from Visual Studio Code

### Using the Test Explorer

1. Open the project folder in VS Code (**File → Open Folder** → select the repo root)
2. Wait for the Java extension to finish indexing (progress shown in the status bar)
3. Click the **Testing** icon in the Activity Bar (flask icon, or `Ctrl+Shift+T` → **Go to Test Explorer**)
4. Expand the tree: `HousingSearchSortingTests` → individual tests are listed
5. Click the **Run** button (▶) next to a test, a class, or the root to run all tests
6. Results appear inline — green tick for pass, red cross for fail with the assertion message shown

> Tests run headed (browser visible) by default. To run headless, use the terminal command below.

### Passing system properties (headless, baseUrl)

The Test Explorer does not support `-D` flags directly. To pass system properties, add them to your VS Code workspace settings:

1. Open **Command Palette** (`Ctrl+Shift+P`) → **Preferences: Open Workspace Settings (JSON)**
2. Add the following (adjust values as needed):

```json
{
  "java.test.config": {
    "vmArgs": ["-Dheadless=true", "-DbaseUrl=https://madrid.craigslist.org"]
  }
}
```

3. Save the file — the Test Explorer will pick up the new arguments on the next run

### Using the Maven sidebar

1. Open the **Maven** panel in the Explorer sidebar (or via **View → Open View → Maven**)
2. Expand **Lifecycle** → double-click **test** to run all tests
3. To run a single test class or method, right-click the `test` lifecycle entry → **Execute Maven Commands** and append `-Dtest=HousingSearchSortingTests`

---

## Running the tests

### Run all tests

```bash
mvn test
```

By default a Chromium window will open visibly. To run headless instead:

```bash
mvn test -Dheadless=true
```

To run against a different environment, pass the `baseUrl` property:

```bash
mvn test -DbaseUrl=https://staging.craigslist.org
```

Without it, the default `https://madrid.craigslist.org` is used.

### Run a single test

```bash
mvn test -Dtest=HousingSearchSortingTests#testHousingPageSortsByPriceDescending
```

### Run a single test class

```bash
mvn test -Dtest=HousingSearchSortingTests
```

---

## Viewing results

### Console

Pass/fail status is printed to the console after each test:

```
========================================
  PASSED: Housing page sorts prices descending correctly
========================================
```

### Surefire report (plain text summary)

```bash
cat target/surefire-reports/TEST-com.teckro.HousingSearchSortingTests.xml
```

### Allure report (full HTML with steps and screenshots)

After a test run, generate and open the report:

```bash
allure serve allure-results
```

> Requires the [Allure CLI](https://allurereport.org/docs/v2/install-for-windows/) to be installed separately.

The Allure report includes:
- Step-by-step breakdown of each test
- Screenshot attached on failure
- Pass/fail history across runs