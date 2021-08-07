plugins {
    kotlin("multiplatform") version "1.5.20"
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
    const val MOCKK = "1.12.0"
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
                implementation("io.ktor:ktor-auth:${Versions.KTOR}")

                implementation("io.ktor:ktor-client-core:${Versions.KTOR}")
                implementation("io.ktor:ktor-client-cio:${Versions.KTOR}")

                implementation("ch.qos.logback:logback-classic:${Versions.LOGBACK}")
            }
        }

        compilations["test"].defaultSourceSet {
            dependencies {
                implementation(kotlin("test-junit5"))

                implementation("org.junit.jupiter:junit-jupiter-api:${Versions.JUNIT}")
                implementation("org.junit.jupiter:junit-jupiter-params:${Versions.JUNIT}")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.JUNIT}")

                implementation("io.ktor:ktor-server-test-host:${Versions.KTOR}")

                implementation("io.mockk:mockk:${Versions.MOCKK}")
            }
        }
    }
}

tasks {
    "wrapper"(Wrapper::class) {
        version = "7.0"
    }

    "serverTest"(Test::class) {
        useJUnitPlatform()
    }
}
