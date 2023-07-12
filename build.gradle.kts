
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform") version "1.9.0"
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.ktlint)
}

group = "dev.lennartegb"
version = "0.1.0"

repositories {
    mavenCentral()
}

kover {
    verify {
        rule {
            bound {
                minValue = 80
            }
        }
    }
}

kotlin {
    nativeTarget()
}

fun KotlinMultiplatformExtension.nativeTarget(
    configure: Action<KotlinNativeTargetWithHostTests> = Action { }
): KotlinNativeTargetWithHostTests {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    return when {
        hostOs == "Mac OS X" -> macosX64("native", configure)
        hostOs == "Linux" -> linuxX64("native", configure)
        isMingwX64 -> mingwX64("native", configure)
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }
}

tasks.register<Copy>("assembleCli") {
    dependsOn(":cli:nativeBinaries")

    from("${project.projectDir}/cli/build/bin/native/releaseExecutable/cli.kexe") {
        rename { project.name }
    }
    into("${project.rootDir}/dist")
}
