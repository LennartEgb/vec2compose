import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
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

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.register<Copy>("packageDistribution") {
    dependsOn("jar")
    from("${project.rootDir}/scripts/vec2compose") {
        filter { it.replace("lib/vec2compose.jar", "${project.rootDir}/dist/lib/vec2compose.jar") }
    }
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
