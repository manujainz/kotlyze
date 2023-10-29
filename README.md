# Kotlyze

Kotlyze is a powerful **Gradle plugin** designed for Kotlin static code analysis. Its primary aim is to assist developers in maintaining clean, consistent, and high-quality code.

## 🚀 Features

- **Code Style Issue Detection:**
  - Deprecated functions, naming conventions, documentation, imports, hardcoded strings, and magic numbers.

- **Complexity Analysis:**
  - Function complexity, deep nesting, condition chains, single responsibility, high cyclomatic complexity, and excessive return statements.

- **Performance Guard:**
  - Coroutine scope misuse, inefficient collections, blocking calls, memory leaks, large bitmap loading, BitmapFactory misuse.

- **Additional Checks:**
  - Detecting public mutable fields, unused imports, multiple classes per file, and trailing whitespace.

... and more to come!

## 🔧 Installation

```groovy
kotlyze {
    targetPath src/main/java/ // specify as per needed
    configPath src/main/res/kotlyze_conf.json // specify as per needed or default config will be used
}
```

## 🛠 Configuration

Specify your desired policy settings in a JSON configuration file. 
Here's an example from the predefined config.

```json
{
  "policies": {
    "MagicNumbersPolicy": {
      "enabled": true
    },
    "MaxCharacterPerLine": {
      "enabled": true,
      "maxAllowedCharPerLine": 120
    },
    // ... other policies
  }
}
```

It is recommended to copy the entire predefined config below and adjust it as per your need.
https://github.com/manujainz/kotlyze/blob/main/src/main/resources/kotlyze_config.json

## 🧪 Features in the Pipeline

- **Parallel Analysis:**
  Introducing parallel processing, significantly reducing the analysis time. 

- **IntelliJ Integration:**
  Real-time issue detection directly within your IntelliJ IDE, highlighting potential problems in the current file as you code.

## 📚 Documentation

Coming soon...

## 📦 Plugin Publication

Coming soon...


🌟 Feel free to provide feedback which helps to extend this further. 