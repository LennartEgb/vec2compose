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

    dependencies {
        commonMainImplementation(project(":core"))
        commonMainImplementation(libs.bundles.xmlutil)
        commonTestImplementation(kotlin("test"))
    }
}
