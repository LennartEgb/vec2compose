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
        commonMain {
            dependencies {
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

/**
 * Resource loading implementation from https://developer.squareup.com/blog/kotlin-multiplatform-shared-test-resources
 */
tasks.register<Copy>("copyNativeTestResources") {
    from("src/commonTest/resources")
    into("build/bin/native/debugTest/resources")
}

tasks.findByName("nativeTest")?.dependsOn("copyNativeTestResources")
