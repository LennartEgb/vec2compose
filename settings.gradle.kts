plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
include(":core")
include(":cli")
include(":svg")
include(":vector-drawable")

rootProject.name = "vec2compose"
