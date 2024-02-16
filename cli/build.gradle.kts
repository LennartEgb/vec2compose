plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.kover)
}

dependencies {
    kover(project(":core"))
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    dependencies {
        commonMainImplementation(project(":core"))
        commonMainImplementation(project(":svg"))
        commonMainImplementation(project(":vector-drawable"))
        commonMainImplementation(libs.kotlin.cli)
        commonMainImplementation(libs.okio)
        commonMainImplementation(libs.koin.core)
        commonTestImplementation(kotlin("test"))
        commonTestImplementation(libs.koin.test)
        commonTestImplementation(libs.okio.fakefilesystem)
    }
}
