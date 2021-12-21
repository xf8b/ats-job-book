/*
 * Copyright (c) 2021 xf8b.
 *
 * This file is part of ats-job-book.
 *
 * ats-job-book is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ats-job-book is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ats-job-book. If not, see <https://www.gnu.org/licenses/>.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile as CompileKotlin

plugins {
    java // language
    kotlin("jvm") version "1.6.10" // language
    application // running
    id("org.openjfx.javafxplugin") version "0.0.10" // javafx
}

group = "io.github.xf8b"
base.archivesName.set("ats-job-book")
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib")) // kotlin
    implementation("ch.qos.logback:logback-classic:1.2.9") // logging
    implementation("com.google.code.gson:gson:2.8.9") // json
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2") // testing
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine") // testing
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("io.github.xf8b.atsjobbook.Main")
}

javafx {
    version = "17"
    modules = listOf("javafx.controls")
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