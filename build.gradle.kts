buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // https://github.com/gradle/gradle/issues/16958#issuecomment-827140071 - fixed in Gradle 7.2
        @Suppress("UnstableApiUsage")
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs") as org.gradle.accessors.dm.LibrariesForLibs
        classpath(libs.gradlePluginsAndroid)
        classpath(libs.gradlePluginsKotlin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
