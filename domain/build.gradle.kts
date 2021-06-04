plugins {
    kotlin("jvm")
    kotlin("kapt")

    id("com.bnorm.power.kotlin-power-assert")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.github.microutils:kotlin-logging:2.0.8")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.5.0")

    implementation(platform("io.arrow-kt:arrow-stack:0.13.2"))
    implementation("io.arrow-kt:arrow-core")

    testImplementation("io.strikt:strikt-core:0.31.0")

    kapt(platform("io.arrow-kt:arrow-stack:0.13.2"))
    kapt("io.arrow-kt:arrow-meta")

    val koTestVersion = "4.6.0"
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("io.kotest:kotest-property:$koTestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")
}
