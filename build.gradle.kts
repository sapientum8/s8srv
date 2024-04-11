import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.serverless"
version = "dev"

description = """hello"""

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
}

repositories {
    mavenCentral()
}

// If requiring AWS JDK, uncomment the dependencyManagement to use the bill of materials
//   https://aws.amazon.com/blogs/developer/managing-dependencies-with-aws-sdk-for-java-bill-of-materials-module-bom/
//dependencyManagement {
//    imports {
//        mavenBom 'com.amazonaws:aws-java-sdk-bom:1.11.688'
//    }
//}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.23")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("com.amazonaws:aws-lambda-java-log4j2:1.6.0")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.0")
}

task<Exec>("deploy") {
    dependsOn("shadowJar")
    commandLine("cmd", "/c", "serverless", "deploy")
}

task<Exec>("deploy-hello") {
    dependsOn("shadowJar")
    commandLine("cmd", "/c", "serverless", "deploy", "function", "-f", "hello")
}

task<Exec>("invoke-hello") {
    dependsOn("shadowJar")
    commandLine("cmd", "/c", "serverless", "invoke", "-f", "hello", "--log")
}

tasks {
    named<ShadowJar>("shadowJar") {
        transform(com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer::class.java)
    }
}

tasks.build {
    finalizedBy("shadowJar")
}