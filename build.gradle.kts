import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.20"
    application
    id("com.gradleup.shadow") version "8.3.7"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

group = "org.github.ringoame196"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val ktorVersion = "3.0.0"
dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-core:${ktorVersion}")
    implementation("io.ktor:ktor-server-netty:${ktorVersion}")
    implementation("io.ktor:ktor-server-html-builder:${ktorVersion}")
    implementation("org.slf4j:slf4j-simple:2.0.13")
}

application {
    mainClass.set("org.github.ringoame196.MainKt")
}

detekt {
    toolVersion = "1.23.6"
    source.setFrom("src/main/kotlin", "src/main/java")
    buildUponDefaultConfig = true
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    ignoreFailures = false
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

// Shadowタスクの設定をひとつに統合
tasks.withType<ShadowJar> {
    archiveBaseName.set("PhotoViewer")
    archiveClassifier.set("")
    archiveVersion.set("")

    manifest {
        attributes["Main-Class"] = "org.github.ringoame196.MainKt"
    }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}