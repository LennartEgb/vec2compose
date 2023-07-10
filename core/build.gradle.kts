@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.cli)
    implementation(libs.bundles.jackson)
    implementation(libs.bundles.xmlutil)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
