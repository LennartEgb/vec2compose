dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(
    ":core",
    ":cli",
    ":svg",
    ":vector-drawable",
)

rootProject.name = "vec2compose"
