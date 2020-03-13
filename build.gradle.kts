plugins {
    kotlin("jvm") version "1.3.70"
    id("application")
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("dev.jacomet.logging-capabilities") version "0.9.0"
    id("com.github.ben-manes.versions") version "0.28.0"
    id("com.bnorm.power.kotlin-power-assert") version "0.3.0"
    id("de.fuerstenau.buildconfig") version "1.1.8"
    id("idea")
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    listOf("org.jetbrains.kotlin:kotlin-stdlib-jdk8",
        "org.jetbrains.kotlin:kotlin-reflect",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4"
    ).forEach { implementation(it) }

    implementation("com.squareup.okhttp3:okhttp:4.4.1")

    listOf("rome", "rome-modules").forEach { implementation("com.rometools:$it:1.12.2") }

    implementation("com.google.apis:google-api-services-youtube:v3-rev20180511-1.27.0")
    implementation("com.google.api-client:google-api-client-jackson2:1.27.0")

    listOf("ktor-server-netty", "ktor-html-builder", "ktor-client-apache").forEach { implementation("io.ktor:$it:1.3.2") }

    implementation("com.yandex.android:disk-restapi-sdk:1.03")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
}

version = "1.0"

val javer = JavaVersion.VERSION_11

java {
    sourceCompatibility = javer
    targetCompatibility = javer
}

tasks {
    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = javer.toString()
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = javer.toString()
            useIR = true
        }
    }

    create("stage") { dependsOn("shadowJar") }
}

application {
    mainClassName = "adeln.MainKt"
}

buildConfig {
    val key = properties["YOUTUBE_API_KEY"] as? String ?: System.getenv("YOUTUBE_API_KEY")
    buildConfigField("String", "YOUTUBE_API_KEY", key)
}

kotlinPowerAssert {
    functions = listOf("kotlin.assert")
}
