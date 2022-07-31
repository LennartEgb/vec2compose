import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    application
}

group = "dev.lennartegb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(dependencyNotation = "com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.3")
    implementation(dependencyNotation = "com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}