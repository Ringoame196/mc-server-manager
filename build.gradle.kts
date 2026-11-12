plugins {
    kotlin("jvm") version "2.0.20"
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

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}