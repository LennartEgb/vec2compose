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
    macosX64()
    linuxX64()
    mingwX64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":core"))
                implementation(libs.bundles.xmlutil)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
