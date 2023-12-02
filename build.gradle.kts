plugins {
    kotlin("jvm") version "1.9.20"
}

group = "nl.rwslinkman"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.20")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}