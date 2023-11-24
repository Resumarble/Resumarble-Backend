import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.6" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20" apply false
    kotlin("plugin.jpa") version "1.9.20" apply false
    kotlin("kapt") version "1.9.20" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1" apply false
}

subprojects {
    group = "resumarble"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://artifactory-oss.prod.netflix.net/artifactory/maven-oss-candidates")
        gradlePluginPortal()
    }

    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.kapt")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude { it.file.path.contains("$buildDir/generated/") }
        }
        disabledRules = listOf("wildcard-imports")
    }

    java.sourceCompatibility = JavaVersion.VERSION_17

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")

        // kotest
        testImplementation("io.kotest:kotest-assertions-core-jvm:5.6.2")
        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.6.2")
        testImplementation("io.mockk:mockk:1.13.5")

        // spring security
        implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
        implementation("org.springframework.boot:spring-boot-starter-security")
        testImplementation("org.springframework.security:spring-security-test")

        // redis
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        testImplementation("it.ozimov:embedded-redis:0.7.2")

        // token
        implementation("com.auth0:java-jwt:3.18.3")
        implementation("io.jsonwebtoken:jjwt:0.9.1")
        implementation("com.nimbusds:nimbus-jose-jwt:9.31")

        runtimeOnly("com.h2database:h2")
        runtimeOnly("com.mysql:mysql-connector-j")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
