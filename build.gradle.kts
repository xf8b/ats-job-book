/*
 * Copyright (c) 2021-2022 xf8b.
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

plugins {
    java // language
    kotlin("jvm") version "1.6.10" // language
    application // running
    id("org.openjfx.javafxplugin") version "0.0.11" // javafx
}

// package details
group = "io.github.xf8b"
base.archivesName.set("ats-job-book")
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10") // kotlin reflection
    implementation("ch.qos.logback:logback-classic:1.2.10") // logging
    implementation("com.google.code.gson:gson:2.8.9") // json
    testImplementation(platform("org.junit:junit-bom:5.8.2")) // testing
    testImplementation("org.junit.jupiter:junit-jupiter") // testing
}

java {
    toolchain {
        // require java 17
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    // enable module-path inferring
    modularity.inferModulePath.set(true)
}

application {
    mainModule.set("io.github.xf8b.atsjobbook")
    mainClass.set("io.github.xf8b.atsjobbook.Main")
}

javafx {
    version = "17.0.1"
    modules("javafx.controls", "javafx.fxml")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "17" // use java 17
            languageVersion = "1.6" // use kotlin 1.6
        }
    }

    jar {
        manifest {
            attributes(
                "Manifest-Version" to "1.0",
                "Main-Class" to "io.github.xf8b.atsjobbook.Main"
            )
        }
    }

    compileJava {
        // set module version
        options.javaModuleVersion.set(provider { project.version as String })
    }

    test {
        // use junit platform for testing
        useJUnitPlatform()
    }
}