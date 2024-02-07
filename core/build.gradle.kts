plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kover)
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    macosX64("macos")
    linuxX64("linux")
    mingwX64("windows")

    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
