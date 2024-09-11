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
    ":compose"
)

rootProject.name = "vec2compose"
