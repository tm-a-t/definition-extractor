plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.23"
}

group = "me.tmat"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation(files("lib/kotlin-grammar-parser-0.1.jar"))
    implementation(files("lib/kotlin-grammar-tools-0.1.jar"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}