@echo off
set DIR=%~dp0
if not defined JAVA_HOME (set JAVACMD=java.exe) else (set JAVACMD=%JAVA_HOME%\bin\java.exe)
if not exist "%DIR%gradle\wrapper\gradle-wrapper.jar" (
  echo gradle-wrapper.jar is intentionally not bundled in this generated source archive.
  echo On a machine with Gradle 8.5+, run: gradle wrapper --gradle-version 8.5
  exit /b 2
)
"%JAVACMD%" -classpath "%DIR%gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
