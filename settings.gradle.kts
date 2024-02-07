plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
include(":core")
include(":cli")
include("svg")
include("vectorDrawable")

rootProject.name = "vec2compose"
