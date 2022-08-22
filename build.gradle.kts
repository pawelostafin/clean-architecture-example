// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.gradleBuildTools apply false
    id("com.android.library") version Versions.gradleBuildTools apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlin apply false
    id("com.github.ben-manes.versions") version Versions.benManesVersions apply true
    id("com.google.gms.google-services") version Versions.googleServices apply false
    id("com.google.firebase.crashlytics") version Versions.firebaseCrashlyticsGradle apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}