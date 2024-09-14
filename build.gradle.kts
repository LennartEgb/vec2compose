plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.ktlint)
}

dependencies {
    kover(project(":core"))
    kover(project(":svg"))
    kover(project(":vector-drawable"))

    kover(project(":cli"))
}

tasks {
    register<Copy>("assembleCli") {
        val systemOs = System.getProperty("os.name")
        val os = when {
            systemOs == "Mac OS X" -> "macosX64"
            systemOs == "Linux" -> "linuxX64"
            systemOs.startsWith("Windows") -> "mingwX64"
            else -> throw GradleException("Host OS is not supported.")
        }

        dependsOn(":cli:${os}Binaries")

        from("${project.projectDir}/cli/build/bin/$os/releaseExecutable/cli.kexe") {
            rename { project.name }
        }
        into("${project.rootDir}/dist")
    }

    register<Exec>("setupGitHooks") {
        commandLine("sh", ".scripts/setupGitHooks")
    }
}
