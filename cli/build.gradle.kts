plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation(project(":core"))
                implementation(libs.kotlin.cli)
                implementation(libs.okio)
            }
        }
        val nativeTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.okio.fakefilesystem)
            }
        }
    }
}
