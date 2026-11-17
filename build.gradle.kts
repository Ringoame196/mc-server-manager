import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.20"
    application
    id("com.gradleup.shadow") version "8.3.7"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

group = "com.github.ringoame196"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val ktorVersion = "3.0.0"
val sqliteVersion = "3.46.1.0"
dependencies {
    testImplementation(kotlin("test"))
    implementation("io.ktor:ktor-server-core:${ktorVersion}")
    implementation("io.ktor:ktor-server-netty:${ktorVersion}")
    implementation("io.ktor:ktor-server-html-builder:${ktorVersion}")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("org.xerial:sqlite-jdbc:$sqliteVersion") // SQLite
}

application {
    mainClass.set("com.github.ringoame196.MainKt")
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

tasks.withType<ShadowJar> {
    // プロジェクト名に合わせる（または設定を削除してデフォルトに従う）
    archiveBaseName.set("mc-server-manager")
    archiveClassifier.set("")
    archiveVersion.set("1.0-SNAPSHOT")

    manifest {
        attributes["Main-Class"] = "com.github.ringoame196.MainKt"
    }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}