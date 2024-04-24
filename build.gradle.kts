import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

description = """s8srv project"""
group = "com.sapientum8"
version = "dev"

plugins {
    java
    jacoco
    `maven-publish`
    alias(libs.plugins.shadow)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.spring.dependency.management)
    //id("org.jetbrains.kotlin.jvm") version "1.9.23"
    //id("org.springframework.boot") version "3.2.5"
    //id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.barfuin.gradle.taskinfo") version "2.2.0"
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

// If requiring AWS JDK, uncomment the dependencyManagement to use the bill of materials
//   https://aws.amazon.com/blogs/developer/managing-dependencies-with-aws-sdk-for-java-bill-of-materials-module-bom/

dependencyManagement {
    imports {
        mavenBom("software.amazon.awssdk:dynamodb:2.25.31")
        mavenBom("software.amazon.awssdk:s3:2.25.31")
    }
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.0-Beta5")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("com.amazonaws:aws-lambda-java-log4j2:1.6.0")
    implementation("software.amazon.awssdk:dynamodb:2.25.35")
    implementation("org.apache.logging.log4j:log4j-api:3.0.0-beta1")
    implementation("org.apache.logging.log4j:log4j-core:3.0.0-beta1")
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.annotations)
    testImplementation(kotlin("test"))
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }
    register<Exec>("deploy") {
        group = "serverless"
        description = "Deploy the serverless application"
        dependsOn("shadowJar")
        commandLine("cmd", "/c", "serverless", "deploy")
    }
    register<Exec>("deploy-hello") {
        group = "serverless"
        description = "Deploy the serverless function hello"
        dependsOn("shadowJar")
        commandLine("cmd", "/c", "serverless", "deploy", "function", "-f", "hello")
    }
    register<Exec>("invoke-hello") {
        group = "serverless"
        description = "Invoke the serverless function hello"
        commandLine("cmd", "/c", "serverless", "invoke", "-f", "hello", "--log")
    }
    named<ShadowJar>("shadowJar") {
        transform(com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer::class.java)
    }
    named<JacocoReport>("jacocoTestReport") {
        dependsOn(test)
    }
    build {
        finalizedBy("shadowJar")
    }
    test {
        useJUnitPlatform()
    }
}