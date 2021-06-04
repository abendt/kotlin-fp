import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    base

    val kotlinVersion = "1.5.10"

    id("org.jetbrains.kotlin.jvm") version kotlinVersion apply false
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false

    id("com.adarshr.test-logger") version "3.0.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0" apply false

    id("org.unbroken-dome.test-sets") version "4.0.0" apply false

    id("com.bnorm.power.kotlin-power-assert") version "0.9.0" apply false
}

val isIdea = System.getProperty("idea.version") != null

subprojects {

    apply(plugin = "com.adarshr.test-logger")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "com.bnorm.power.kotlin-power-assert")

    // global ktlint settings
    this.extensions.configure(KtlintExtension::class.java) {
        disabledRules.set(setOf("import-ordering", "no-wildcard-imports"))
        verbose.set(true)
        version.set("0.41.0")
    }

    plugins.withType(JavaPlugin::class.java).configureEach {
        extensions.getByType(JavaPluginExtension::class.java).let {
            it.sourceCompatibility = JavaVersion.VERSION_11
            it.targetCompatibility = JavaVersion.VERSION_11
        }
    }

    testlogger {
        // idea can't handle ANSI output
        setTheme(if (isIdea) "plain" else "mocha")
        showFullStackTraces = false
    }

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            apiVersion = "1.5"
            languageVersion = "1.5"
            freeCompilerArgs = listOf("-Xjsr305=strict", "-progressive")
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        jvmArgs!!.add("-ea")
    }

}
