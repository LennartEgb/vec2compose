plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kover)
}

kotlin {
    jvm()
    macosX64()
    linuxX64()
    mingwX64()

    dependencies {
        commonMainImplementation(project(":core"))
        commonMainImplementation(libs.bundles.xmlutil)
        commonMainImplementation(libs.koin.core)
        commonTestImplementation(libs.koin.test)
        commonTestImplementation(kotlin("test"))
    }
}
