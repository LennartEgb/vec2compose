import kotlinx.kover.api.KoverTaskExtension
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.ktlint)
    application
}

group = "dev.lennartegb"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.cli)
    implementation(libs.bundles.jackson)

    testImplementation(kotlin("test"))
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

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showCauses = true
        showExceptions = true
        showStackTraces = true
    }
    extensions.configure(KoverTaskExtension::class) {
        isDisabled.set(false)
        reportFile.set(file("$buildDir/kover/result.bin"))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.register<Copy>("packageDistribution") {
    dependsOn(":cli:jar")
    from("${project.rootDir}/scripts/vec2compose") {
        val jarName = "lib/${project.name}.jar"
        filter { it.replace(jarName, "${project.rootDir}/dist/$jarName") }
    }
    from("${project.projectDir}/cli/build/libs/cli.jar") {
        rename { "${project.name}.jar" }
        into("lib")
    }
    into("${project.rootDir}/dist")
}
