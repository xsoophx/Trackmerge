plugins {
    kotlin("multiplatform") version "1.5.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx")
}

object Versions {
    const val JACKSON = "2.12.4"
    const val JUNIT = "5.7.2"
    const val KTOR = "1.6.2"
    const val LOGBACK = "1.2.5"
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    js {
        browser {
            webpackTask {
                output.libraryTarget = "this"
            }
            binaries.executable()
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
    }

    jvm("server") {
        compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))

                implementation("io.ktor:ktor-server-netty:${Versions.KTOR}")
                implementation("io.ktor:ktor-server-core:${Versions.KTOR}")
                implementation("io.ktor:ktor-html-builder:${Versions.KTOR}")
                implementation("io.ktor:ktor-jackson:${Versions.KTOR}")

                implementation("ch.qos.logback:logback-classic:${Versions.LOGBACK}")

                implementation("com.fasterxml.jackson.core:jackson-core:${Versions.JACKSON}")
                implementation("com.fasterxml.jackson.core:jackson-databind:${Versions.JACKSON}")
                implementation("com.fasterxml.jackson.core:jackson-annotations:${Versions.JACKSON}")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.JACKSON}")
            }
        }

        compilations["test"].defaultSourceSet {
            dependencies {
                implementation(kotlin("test-junit5"))

                implementation("org.junit.jupiter:junit-jupiter-api:${Versions.JUNIT}")
                implementation("org.junit.jupiter:junit-jupiter-params:${Versions.JUNIT}")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.JUNIT}")

                implementation("io.ktor:ktor-server-test-host:${Versions.KTOR}")
            }
        }
    }
}

tasks {
    "wrapper"(Wrapper::class) {
        version = "5.4.1"
    }

    "serverTest"(Test::class) {
        useJUnitPlatform()
    }
}
