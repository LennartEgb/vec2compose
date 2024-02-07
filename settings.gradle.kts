plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
include(":core")
include(":cli")
rootProject.name = "vec2compose"
include("svg")
