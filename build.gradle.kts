import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    application
}

group = "dev.lennartegb"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(dependencyNotation = "org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
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

tasks.register<Copy>("packageDistribution") {
    dependsOn("jar")
    from("${project.rootDir}/scripts/vec2compose")
    from("${project.projectDir}/build/libs/${project.name}-${project.version}.jar") {
        rename { "${project.name}.jar" }
        into("lib")
    }
    into("${project.rootDir}/dist")
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
        exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

application {
    mainClass.set("MainKt")
}
