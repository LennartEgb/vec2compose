pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":core",
    ":cli",
    ":svg",
    ":vector-drawable",
    ":composeApp"
)

rootProject.name = "vec2compose"
