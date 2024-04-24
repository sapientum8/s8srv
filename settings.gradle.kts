rootProject.name = "s8srv"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin-version", "1.9.23")
            version("fasterxml-version", "2.17.0")
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin-version")
            plugin("spring-dependency-management", "io.spring.dependency-management").version("1.1.4")
            plugin("shadow-plugin", "com.github.johnrengelman.shadow").version("8.1.1")
            library("jackson-core", "com.fasterxml.jackson.core","jackson-core").versionRef("fasterxml-version")
            library("jackson-databind", "com.fasterxml.jackson.core","jackson-databind").versionRef("fasterxml-version")
            library("jackson-annotations", "com.fasterxml.jackson.core","jackson-annotations").versionRef("fasterxml-version")
        }
    }
}