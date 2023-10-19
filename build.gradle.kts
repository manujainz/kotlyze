import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application
    `kotlin-dsl`
    id("maven-publish")
    id("java-gradle-plugin")
}

group = "com.manujainz"
version = "1.0.SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.6.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.json:json:20231013")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Tar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<Zip> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

gradlePlugin {
    plugins {
        create("kotlyze") {
            id = "com.manujainz.kotlyze"
            implementationClass = "com.manujainz.kotlyze.plugin.KotlyzePlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "com.manujainz.kotlyze"
            artifactId = "kotlyze-plugin"
            version = this.version
        }
    }

    repositories {
        mavenLocal()
    }
}




