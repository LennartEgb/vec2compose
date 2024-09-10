plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.kover)
    alias(libs.plugins.ktlint)
}

kotlin {
    configure(listOf(macosX64(), linuxX64(), mingwX64())) {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core"))
            implementation(project(":svg"))
            implementation(project(":vector-drawable"))
            implementation(libs.kotlin.cli)
            implementation(libs.okio)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.okio.fakefilesystem)
        }
    }
}
