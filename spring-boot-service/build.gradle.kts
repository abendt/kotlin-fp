import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    kotlin("jvm")
    kotlin("kapt")

    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.unbroken-dome.test-sets")
}

testSets {
    val integrationTest by creating {}
}

val developmentOnly by configurations.getting
val integrationTestImplementation by configurations.getting

configurations.getByName("runtimeClasspath") {
    extendsFrom(developmentOnly)
}

val restAssuredVersion = "4.4.0"

configurations {
    all {
        exclude(module = "log4j")
        exclude(module = "mockito-core")

        resolutionStrategy.eachDependency {
            if (requested.group == "io.rest-assured") {
                useVersion(restAssuredVersion)
            }
        }
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":domain"))

    implementation("io.github.microutils:kotlin-logging:2.0.8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.0")

    implementation(platform("io.arrow-kt:arrow-stack:0.13.2"))
    implementation("io.arrow-kt:arrow-core")
    implementation("io.arrow-kt:arrow-fx-coroutines")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation(
        "io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.3"
    )
    testImplementation("io.strikt:strikt-core:0.31.0")

    kapt(platform("io.arrow-kt:arrow-stack:0.13.2"))
    kapt("io.arrow-kt:arrow-meta")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation(platform("org.junit:junit-bom:5.7.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.mockk:mockk:1.11.0")

    testImplementation("io.kotest:kotest-runner-junit5:4.6.0")

    integrationTestImplementation("io.kotest.extensions:kotest-extensions-spring:1.0.0")
    integrationTestImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
        exclude(module = "mockito-core")
    }

    integrationTestImplementation("com.ninja-squad:springmockk:3.0.1")
}

tasks {
    val bootRun by getting(BootRun::class) {
        args("--spring.profiles.active=local")
        workingDir = file("src/test/resources")
    }

    val integrationTest by getting {
        shouldRunAfter("test")
    }

    val check by getting {
        dependsOn(integrationTest)
    }
}
