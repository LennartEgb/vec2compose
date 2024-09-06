plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.ktlint)
}

dependencies {
    kover(project(":core"))
    kover(project(":svg"))
    kover(project(":vector-drawable"))

    kover(project(":cli"))
}

tasks.register<Copy>("assembleCli") {
    dependsOn(":cli:nativeBinaries")

    from("${project.projectDir}/cli/build/bin/native/releaseExecutable/cli.kexe") {
        rename { project.name }
    }
    into("${project.rootDir}/dist")
}
