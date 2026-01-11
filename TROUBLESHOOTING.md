# Troubleshooting ExceptionInInitializerError com.sun.tools.javac.code.TypeTag :: UNKNOWN

## Root Causes and Solutions:

### 1. Java Version Mismatch
- This error often occurs when Maven plugins are compiled with a different Java version than your runtime
- Ensure you're using Java 11 consistently

### 2. Clean Build Environment
Run these commands to clean your environment:
```bash
mvn clean
rm -rf target/
rm -rf ~/.m2/repository/org/apache/maven/
```

### 3. Maven Compiler Plugin Issues
The error is often resolved by:
- Using Maven Compiler Plugin 3.8.1 or 3.10.1 (avoid 3.11.x)
- Removing the `<release>` configuration
- Using explicit `<source>` and `<target>` instead

### 4. Lombok Annotation Processing
- Update Lombok to latest version (1.18.30+)
- Ensure annotation processor path is correctly configured
- Clear annotation processor cache

### 5. Environment Variables
Ensure these are set:
- JAVA_HOME pointing to JDK 11
- PATH includes JAVA_HOME/bin
- Maven using same Java version

### 6. IDE Configuration
- Restart IDE after Maven changes
- Re-import Maven project
- Clear IDE caches

### 7. Alternative Solution: Downgrade Spring Boot
If issues persist, consider downgrading to Spring Boot 2.5.3 or earlier, as version 2.5.5 has some compatibility quirks.

## Commands to Test:
```bash
# Clean everything
mvn clean

# Compile with debug info
mvn compile -X -e

# Check effective POM
mvn help:effective-pom
```
