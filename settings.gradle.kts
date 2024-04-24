rootProject.name = "s8srv"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin-version", "1.9.23")
            version("fasterxml-version", "2.17.0")
            version("shadow-plugin-version", "8.1.1")
            version("spring-dependency-version", "1.1.4")
            version("spring-boot-version", "3.2.5")
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin-version")
            plugin("spring-dependency-management", "io.spring.dependency-management").versionRef("spring-dependency-version")
            plugin("spring-boot", "org.springframework.boot").versionRef("spring-boot-version")
            plugin("shadow-plugin", "com.github.johnrengelman.shadow").versionRef("shadow-plugin-version")
            library("jackson-core", "com.fasterxml.jackson.core","jackson-core").versionRef("fasterxml-version")
            library("jackson-databind", "com.fasterxml.jackson.core","jackson-databind").versionRef("fasterxml-version")
            library("jackson-annotations", "com.fasterxml.jackson.core","jackson-annotations").versionRef("fasterxml-version")
        }
    }
}