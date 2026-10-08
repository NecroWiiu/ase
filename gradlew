#!/bin/sh
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
if [ -x "$JAVA_HOME/bin/java" ]; then JAVACMD="$JAVA_HOME/bin/java"; else JAVACMD="java"; fi
if ! command -v "$JAVACMD" >/dev/null 2>&1; then echo "Java 17+ is required." >&2; exit 1; fi
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
if [ ! -f "$WRAPPER_JAR" ]; then
  echo "gradle-wrapper.jar is intentionally not bundled in this generated source archive." >&2
  echo "On a machine with Gradle 8.5+, run: gradle wrapper --gradle-version 8.5" >&2
  exit 2
fi
exec "$JAVACMD" -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
