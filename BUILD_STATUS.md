# Build status of this generated archive

The repository source was reviewed for JSON syntax and Java brace integrity in the generation environment.

A real Gradle/Minecraft compilation was not performed here because this environment does not include Gradle and external dependency downloads are unavailable. The repository includes `.github/workflows/build.yml`, which performs the real build in GitHub Actions with Java 17 and Gradle 8.5.

For a local build, install Java 17 + Gradle 8.5, then run:

```bash
gradle build
```

To generate Minecraft development sources with Loom:

```bash
gradle genSources
```
