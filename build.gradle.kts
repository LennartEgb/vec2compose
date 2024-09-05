plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.kover)
}

dependencies {
    kover(project(":cli"))
}

tasks.register<Copy>("assembleCli") {
    dependsOn(":cli:nativeBinaries")

    from("${project.projectDir}/cli/build/bin/native/releaseExecutable/cli.kexe") {
        rename { project.name }
    }
    into("${project.rootDir}/dist")
}
