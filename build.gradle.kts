import org.jetbrains.kotlin.gradle.tasks.KotlinCompile as CompileKotlin

plugins {
    java
    kotlin("jvm") version "1.6.10"
    application
    idea
}

group = "io.github.xf8b"
base.archivesName.set("ats-job-book")
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

application {
    mainClass.set("io.github.xf8b.atsjobbook.Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<CompileKotlin>().configureEach {
        kotlinOptions {
            jvmTarget = "17"
            languageVersion = "1.6"
        }
    }
}