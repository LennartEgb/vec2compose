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

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    val commonMain by creating {
        dependencies {
            implementation(libs.bundles.xmlutil)
            testImplementation(kotlin("test"))
        }
    }
}
